package com.example.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.entity.MailDetail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String sender;

	@Override
	public String sendMail(MailDetail mailDetail) {
		// Try block to check for exceptions handling
		try {

			// Creating a simple mail message object
			SimpleMailMessage emailMessage = new SimpleMailMessage();

			// Setting up necessary details of mail
			emailMessage.setFrom(sender);
			emailMessage.setTo(mailDetail.getRecipient());
			emailMessage.setSubject(mailDetail.getSubject());
			emailMessage.setText(mailDetail.getMsgBody());

			// Sending the email
			mailSender.send(emailMessage);
			return "Email has been sent successfully...";
		}

		// Catch block to handle the exceptions
		catch (Exception e) {
			return "Error while Sending email!!!";
		}
	}


	@Override
	public String sendMailWithAttachment(MailDetail mailDetail) {
		// Creating a mime message
        MimeMessage mimeMessage
            = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {

            // Setting multipart as true for attachments to
            // be send
            mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(mailDetail.getRecipient());
            mimeMessageHelper.setText(mailDetail.getMsgBody());
            mimeMessageHelper.setSubject(
            		mailDetail.getSubject());

            // Adding the attachment
            FileSystemResource file
                = new FileSystemResource(
                    new File(mailDetail.getAttachment()));

            mimeMessageHelper.addAttachment(
                file.getFilename(), file);
                
            // Sending the mail
            mailSender.send(mimeMessage);
            return "Mail sent Successfully";
        }

        // Catch block to handle MessagingException
        catch (MessagingException e) {

            // Display message when exception occurred
            return "Error while sending mail!!!";
        }
	}
}