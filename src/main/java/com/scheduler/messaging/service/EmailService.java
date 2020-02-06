package com.scheduler.messaging.service;

import java.util.List;

public interface EmailService {
	public void sendSimpleMessage(List<String> to, String subject, String text,List<String> cc);
}
