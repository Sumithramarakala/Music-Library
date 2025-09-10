package com.example.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.MailService;
import com.example.entity.EmailLog;
import com.example.entity.MailDetail;
import com.example.repository.EmailLogRepository;

@RestController
public class MailController {

    @Autowired
    private MailService mailService;

    @Autowired
    private EmailLogRepository emailLogRepository;

    @PostMapping("/sendMail")
    public String sendMail(@RequestBody MailDetail mailDetail) {
        // Map MailDetail to EmailLog
        EmailLog emailLog = new EmailLog();
        emailLog.setRecipient(mailDetail.getRecipient());
        emailLog.setSubject(mailDetail.getSubject());
        emailLog.setMsgBody(mailDetail.getMsgBody());
       // emailLog.setAttachment(mailDetail.getAttachment());
        emailLog.setTimestamp(LocalDateTime.now());

        // Save to database
        emailLogRepository.save(emailLog);

        return "Email sent successfully";
    }




//@RestController
//@RequestMapping("/api")
//public class MailController {
//@Autowired
//private MailService mailService;

//Sending email
/*
 * {

"recipient": "sumithramarakala@gmail.com",
"subject":"Test mail",
"msgBody":" Project for mailing service"*/
//@PostMapping("/send-mail")
//public String sendMail(@RequestBody MailDetail mailDetail) {
//return mailService.sendMail(mailDetail);
//}
//Sending email with attachment
/*
 * {

"recipient": "sumithramaraka@gmail.com",
"subject":"Test mail",
"msgBody":" Project for mailing service",
"attachment" : "C:\\Users\\Niti Dwivedi\\Pictures\\IO\\Inputoutput.png"

}
 */
@PostMapping("/send-mail-attachment")
public String sendMailWithAttachment(@RequestBody MailDetail mailDetail)
{
return mailService.sendMailWithAttachment(mailDetail);
}
}