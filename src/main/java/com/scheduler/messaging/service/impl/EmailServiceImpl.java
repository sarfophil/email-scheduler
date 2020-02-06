package com.scheduler.messaging.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import com.scheduler.messaging.service.EmailService;

import lombok.extern.slf4j.Slf4j;

/**
 * Email Service Component for sending mails
 * @author SARFO PHILIP
 */
@Slf4j
@Component
public class EmailServiceImpl implements EmailService {

	@Autowired
	public JavaMailSender emailSender;

	@Override
	public void sendSimpleMessage(List<String> to, String subject, String text, List<String> cc) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo((String[]) to.toArray());
			message.setSubject(subject);
			message.setCc((String[]) cc.toArray());
			message.setText(text);
			emailSender.send(message);
		} catch (Exception e) {
			log.info("Mail sending failed");
		}
	}

}
