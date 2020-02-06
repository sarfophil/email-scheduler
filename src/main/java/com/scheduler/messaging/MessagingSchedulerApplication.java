package com.scheduler.messaging;


import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class MessagingSchedulerApplication {


	public static void main(String[] args) {
		SpringApplication.run(MessagingSchedulerApplication.class, args);
	}

	@Bean
	public Scheduler startSchedulerFactory() {
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Scheduler scheduler = null;
		try {
			scheduler = schedulerFactory.getScheduler();
			if (!scheduler.isStarted())
				scheduler.start();
		} catch (SchedulerException e) {
			log.info("Error occured whiles starting scheduler");
		}
		return scheduler;
	}
	
	@Bean
	public JavaMailSender emailSender() {
		return new JavaMailSenderImpl();
	}

}
