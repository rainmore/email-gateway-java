package au.com.rainmore.controllers.v1.dto;

import java.io.Serializable;

public class MessageDto implements Serializable {

    private String from;
    private String replyTo;
    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String content;

    public String getFrom() {
        return from;
    }

    public MessageDto setFrom(String from) {
        this.from = from;
        return this;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public MessageDto setReplyTo(String replyTo) {
        this.replyTo = replyTo;
        return this;
    }

    public String getTo() {
        return to;
    }

    public MessageDto setTo(String to) {
        this.to = to;
        return this;
    }

    public String getCc() {
        return cc;
    }

    public MessageDto setCc(String cc) {
        this.cc = cc;
        return this;
    }

    public String getBcc() {
        return bcc;
    }

    public MessageDto setBcc(String bcc) {
        this.bcc = bcc;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public MessageDto setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getContent() {
        return content;
    }

    public MessageDto setContent(String content) {
        this.content = content;
        return this;
    }
}
