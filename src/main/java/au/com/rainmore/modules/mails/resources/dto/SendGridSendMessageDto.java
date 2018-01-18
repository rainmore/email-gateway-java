package au.com.rainmore.modules.mails.resources.dto;

import au.com.rainmore.modules.mails.dto.Message;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.MediaType;

import javax.mail.internet.InternetAddress;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SendGridSendMessageDto implements Serializable {

    private final Mail                  from;
    private final Mail                  replyTo;
    private final String                subject;
    private final List<Personalization> personalizations;
    private final List<Content>         content;


    public SendGridSendMessageDto(Message message) {
        this.from = new Mail(message.getFrom());
        this.replyTo = new Mail(Optional.ofNullable(message.getReplyTo()).orElse(message.getFrom()));
        this.subject = message.getSubject();

        this.content = new ArrayList<>();
        this.content.add(Content.text(message.getContent()));

        this.personalizations = new ArrayList<>();

        List<Mail> to = null;
        List<Mail> cc = null;
        List<Mail> bcc = null;
        if (!message.getTo().isEmpty()) {
            to = message.getTo().stream().map(Mail::new).collect(Collectors.toList());
        }
        if (!message.getCc().isEmpty()) {
            cc = message.getCc().stream().map(Mail::new).collect(Collectors.toList());
        }
        if (!message.getBcc().isEmpty()) {
            bcc = message.getBcc().stream().map(Mail::new).collect(Collectors.toList());
        }

        this.personalizations.add(new Personalization(to, cc, bcc, subject));
    }

    public Mail getFrom() {
        return from;
    }

    public Mail getReplyTo() {
        return replyTo;
    }

    public String getSubject() {
        return subject;
    }

    public List<Personalization> getPersonalizations() {
        return personalizations;
    }

    public List<Content> getContent() {
        return content;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Mail implements Serializable {
        private final String email;
        private final String name;

        public Mail(String email, String name) {
            this.email = email;
            this.name = name;
        }

        public Mail(InternetAddress internetAddress) {
            this(internetAddress.getAddress(), internetAddress.getPersonal());
        }

        public String getEmail() {
            return email;
        }

        public String getName() {
            return name;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Personalization implements Serializable {
        private final List<Mail> to;
        private final List<Mail> cc;
        private final List<Mail> bcc;
        private final String     subject;

        public Personalization(List<Mail> to, List<Mail> cc, List<Mail> bcc, String subject) {
            this.to = to;
            this.cc = cc;
            this.bcc = bcc;
            this.subject = subject;
        }

        public List<Mail> getTo() {
            return to;
        }

        public List<Mail> getCc() {
            return cc;
        }

        public List<Mail> getBcc() {
            return bcc;
        }

        public String getSubject() {
            return subject;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Content implements Serializable {
        private final MediaType mediaType;
        private final String    value;

        public Content(MediaType mediaType, String value) {
            this.mediaType = mediaType;
            this.value = value;
        }

        public static Content text(String value) {
            return new Content(MediaType.TEXT_PLAIN, value);
        }

        public static Content html(String value) {
            return new Content(MediaType.TEXT_HTML, value);
        }

        @JsonIgnore
        public MediaType getMediaType() {
            return mediaType;
        }

        public String getType() {
            return mediaType.toString();
        }

        public String getValue() {
            return value;
        }
    }

}
