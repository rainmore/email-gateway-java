package au.com.rainmore.modules.mails.dto;

import javax.mail.internet.InternetAddress;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Message implements Serializable {

    private InternetAddress      from;
    private InternetAddress      replyTo;
    private Set<InternetAddress> to = new HashSet<>();
    private Set<InternetAddress> cc = new HashSet<>();
    private Set<InternetAddress> bcc = new HashSet<>();
    private String               subject;
    private String               content;

    public InternetAddress getFrom() {
        return from;
    }

    public Message setFrom(InternetAddress from) {
        this.from = from;
        return this;
    }

    public InternetAddress getReplyTo() {
        return replyTo;
    }

    public Message setReplyTo(InternetAddress replyTo) {
        this.replyTo = replyTo;
        return this;
    }

    public Set<InternetAddress> getTo() {
        return to;
    }

    public Message setTo(Set<InternetAddress> to) {
        this.to = to;
        return this;
    }

    public Set<InternetAddress> getCc() {
        return cc;
    }

    public Message setCc(Set<InternetAddress> cc) {
        this.cc = cc;
        return this;
    }

    public Set<InternetAddress> getBcc() {
        return bcc;
    }

    public Message setBcc(Set<InternetAddress> bcc) {
        this.bcc = bcc;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public Message setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Message setContent(String content) {
        this.content = content;
        return this;
    }
}
