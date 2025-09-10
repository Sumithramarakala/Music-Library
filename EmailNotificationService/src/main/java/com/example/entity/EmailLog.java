package com.example.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class EmailLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recipient;
    private String subject;
    private String msgBody;
   // private String attachment;
    private LocalDateTime timestamp;

    public EmailLog() {}

    public EmailLog(String recipient, String subject, String msgBody, String attachment, LocalDateTime timestamp) {
        this.recipient = recipient;
        this.subject = subject;
        this.msgBody = msgBody;
        //this.attachment = attachment;
        this.timestamp = timestamp;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

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

//    public String getAttachment() {
//        return attachment;
//    }
//    public void setAttachment(String attachment) {
//        this.attachment = attachment;
//    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
