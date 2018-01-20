package au.com.rainmore.controllers;

import au.com.rainmore.modules.mails.resources.dto.ResponseDto;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController("controllers.defaultController")
public class DefaultController implements ErrorController {
    private static final String ERROR_PATH = "/error";

    @RequestMapping(ERROR_PATH)
    public ResponseEntity<ResponseDto> error(HttpServletResponse response) {
        HttpStatus status = HttpStatus.valueOf(response.getStatus());
        ResponseDto responseDto = new ResponseDto();
        responseDto.getErrors().put("status", String.valueOf(status.value()));
        responseDto.getErrors().put("reason", status.getReasonPhrase());
        return new ResponseEntity<>(responseDto, status);
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

}
