package au.com.rainmore.modules.mails.resources;

import au.com.rainmore.configs.mails.MailsProperties;
import au.com.rainmore.modules.mails.dto.Message;
import au.com.rainmore.modules.mails.resources.dto.SendGridSendMessageDto;
import au.com.rainmore.modules.mails.resources.dto.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@Component
public class SendGridResource implements Resources<ResponseDto> {

    private static final String API_SEND_URL = "https://api.sendgrid.com/v3/mail/send";

    @Autowired
    private MailsProperties properties;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public ResponseEntity<ResponseDto> send(Message message) throws RestClientException {
        if (getApiKey() == null) {
            throw new RestClientException("Empty API KEY");
        }

        SendGridSendMessageDto dto = new SendGridSendMessageDto(message);
        HttpEntity<SendGridSendMessageDto> request = new HttpEntity<>(new SendGridSendMessageDto(message), buildHeaders());
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(API_SEND_URL, HttpMethod.POST, request, ResponseDto.class);

    }

    private String getApiKey() {
        return properties.getSendGrid().getApiKey();
    }

    private MultiValueMap<String, String> buildHeaders() {

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", String.format("Bearer %s", getApiKey()));
        headers.add("content-type", MediaType.APPLICATION_JSON_VALUE);

        return headers;
    }
}
