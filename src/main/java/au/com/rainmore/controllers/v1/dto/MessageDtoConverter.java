package au.com.rainmore.controllers.v1.dto;

import au.com.rainmore.controllers.Converter;
import au.com.rainmore.modules.mails.dto.Message;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class MessageDtoConverter implements Converter<Message, MessageDto>{

    @Override
    public Message from(MessageDto dto) {
        Message entity = new Message();
        entity.setSubject(dto.getSubject());
        entity.setContent(dto.getContent());

        if (Optional.ofNullable(dto.getFrom()).isPresent()) {
            entity.setFrom(parseInternetAddress(dto.getFrom()).stream().findFirst().orElse(null));
        }

        if (Optional.ofNullable(dto.getReplyTo()).isPresent()) {
            entity.setReplyTo(parseInternetAddress(dto.getReplyTo())
                    .stream().findFirst().orElse(null));
        }

        if (Optional.ofNullable(dto.getTo()).isPresent()) {
            entity.setTo(parseInternetAddress(dto.getTo()));
        }

        if (Optional.ofNullable(dto.getCc()).isPresent()) {
            entity.setCc(parseInternetAddress(dto.getCc()));
        }

        if (Optional.ofNullable(dto.getBcc()).isPresent()) {
            entity.setBcc(parseInternetAddress(dto.getBcc()));
        }
        return entity;
    }

    private Set<InternetAddress> parseInternetAddress(String address) {
        Set<InternetAddress> internetAddress = new HashSet<>();
        try {
            internetAddress = new HashSet<>(Arrays.asList(InternetAddress.parseHeader(address, true)));
        }
        catch (AddressException e) {
            // we don't need to handle it
        }
        return internetAddress;
    }
}
