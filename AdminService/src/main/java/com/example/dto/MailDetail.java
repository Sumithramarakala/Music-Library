package com.example.dto;

public class MailDetail {
    private String recipient;
    private String subject;
    private String msgBody;

    // Constructors
    public MailDetail() {}

    public MailDetail(String recipient, String subject, String msgBody) {
        this.recipient = recipient;
        this.subject = subject;
        this.msgBody = msgBody;
    }

    // Getters and setters
    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }
}
