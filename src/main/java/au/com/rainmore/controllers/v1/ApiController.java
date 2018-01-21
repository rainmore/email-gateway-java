package au.com.rainmore.controllers.v1;

import au.com.rainmore.controllers.v1.dto.MessageDto;
import au.com.rainmore.controllers.v1.dto.MessageDtoConverter;
import au.com.rainmore.controllers.v1.validators.MessageDtoValidator;
import au.com.rainmore.modules.mails.dto.Message;
import au.com.rainmore.modules.mails.resources.MailGunResource;
import au.com.rainmore.modules.mails.resources.Resources;
import au.com.rainmore.modules.mails.resources.SendGridResource;
import au.com.rainmore.modules.mails.resources.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/v1")
public class ApiController {
    private final Logger logger = LoggerFactory.getLogger(ApiController.class);

    private MessageDtoConverter messageDtoConverter = new MessageDtoConverter();

    @Autowired
    private List<Resources<ResponseDto>>  mailResources;
    @Autowired
    private MailGunResource  mailGun;
    @Autowired
    private SendGridResource sendGrid;

    @InitBinder("messageDto")
    private void initMessageDtoValidator(WebDataBinder binder) {
        binder.addValidators(new MessageDtoValidator());
    }

    @PostMapping("/mail/send")
    public ResponseEntity<ResponseDto> send(@Valid @RequestBody MessageDto messageDto) {
        Message message = messageDtoConverter.from(messageDto);
        return sendMessage(message);
    }

    @PostMapping("/mail/send/mail-gun")
    public ResponseEntity<ResponseDto> sendMailGun(@Valid @RequestBody MessageDto messageDto) {
        Message message = messageDtoConverter.from(messageDto);
        return unifyResponse(mailGun.send(message));
    }

    @PostMapping("/mail/send/send-grid")
    public ResponseEntity<ResponseDto> sendSendGrid(@Valid @RequestBody MessageDto messageDto) {
        Message message = messageDtoConverter.from(messageDto);
        return unifyResponse(sendGrid.send(message));
    }

    private ResponseEntity<ResponseDto> sendMessage(Message message) throws RestClientException {
        Collections.shuffle(mailResources);
        Resources<ResponseDto> resources1 = mailResources.get(0);
        Resources<ResponseDto> resources2 = mailResources.size() > 1 ? mailResources.get(mailResources.size() - 1) : null;

        ResponseEntity<ResponseDto> responseEntity = null;
        try {
            responseEntity = resources1.send(message);
        }
        catch (RestClientException ex) {

            if (Optional.ofNullable(resources2).isPresent()) {
                responseEntity = resources2.send(message);
            }
        }

        return unifyResponse(responseEntity);
    }

    private ResponseEntity<ResponseDto> unifyResponse(ResponseEntity<ResponseDto> responseEntity) {
        // To provide unified response across MailGun and SendGrid
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            ResponseDto responseDto = new ResponseDto();
            responseDto.getInfo().put("message", "Message is sent successfully");
            return ResponseEntity.ok(responseDto);
        }
        else {
            return responseEntity;
        }
    }

    @ExceptionHandler({RestClientException.class})
    private ResponseEntity<String> handleRestClientException(RestClientException exception, HttpServletRequest request) {
        logger.error(exception.getMessage(), exception);
        HttpStatusCodeException ex = (HttpStatusCodeException) exception;
        return new ResponseEntity<String>(ex.getMessage(), ex.getStatusCode());
    }
}
