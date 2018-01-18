package au.com.rainmore.modules.mails.resources;

import au.com.rainmore.configs.mails.MailsProperties;
import au.com.rainmore.modules.mails.dto.Message;
import au.com.rainmore.modules.mails.resources.dto.ResponseDto;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.mail.internet.InternetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
public class MailGunResource implements Resources<ResponseDto> {

    private static final String API_SEND_URL_TEMPLE = "https://api.mailgun.net/v3/%s/messages";

    @Autowired
    private MailsProperties properties;

    @Override
    public ResponseEntity<ResponseDto> send(Message message) throws RestClientException {
        if (getApiKey() == null) {
            throw new RestClientException("Empty API KEY");
        }

        if (getDomainName() == null) {
            throw new RestClientException("Empty Domain Name");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<Object> request = new HttpEntity<>(buildPostData(message), headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("api", getApiKey()));

        return restTemplate.exchange(getApiSendUrl(), HttpMethod.POST, request, ResponseDto.class);
    }

    private String getApiKey() {
        return properties.getMailGun().getApiKey();
    }

    private String getDomainName() {
        return properties.getMailGun().getDomain();
    }

    private String getApiSendUrl() {
        return String.format(API_SEND_URL_TEMPLE, getDomainName());
    }

    private MultiValueMap<String, String> buildPostData(Message message) {
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("from", message.getFrom().toString());
        data.add("subject", message.getSubject());
        data.add("text", message.getContent());

        InternetAddress replyTo = Optional.ofNullable(message.getReplyTo()).orElse(message.getFrom());
        data.add("h:Reply-To", new String(Base64.encodeBase64(replyTo.toString().getBytes(StandardCharsets.US_ASCII))));

        message.getTo().forEach(internetAddress -> data.add("to", internetAddress.toString()));
        message.getCc().forEach(internetAddress -> data.add("cc", internetAddress.toString()));
        message.getBcc().forEach(internetAddress -> data.add("bcc", internetAddress.toString()));

        return data;
    }
}
