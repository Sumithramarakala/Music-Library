package com.example.service;

import com.example.entity.MailDetail;

public interface MailService {
	 String sendMail(MailDetail mailDetail);
	 String sendMailWithAttachment(MailDetail mailDetail);
	 }