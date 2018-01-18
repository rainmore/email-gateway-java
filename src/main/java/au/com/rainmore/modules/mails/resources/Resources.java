package au.com.rainmore.modules.mails.resources;

import au.com.rainmore.modules.mails.dto.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

public interface Resources<T> {
    ResponseEntity<T> send(Message message) throws RestClientException;
}
