package com.scheduler.messaging.job;

import com.scheduler.messaging.dto.MessageDetail;
import com.scheduler.messaging.service.EmailService;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Responsible for executing current jobs from quartz scheduler
 * @author SARFO OWUSU PHILIP
 */
@Slf4j
public class JobExecutor extends QuartzJobBean {

	@Autowired
	private EmailService emailService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		JobDetail jobDetail = context.getJobDetail();
		JobDataMap dataMap = jobDetail.getJobDataMap();

		// Retrieves MessageDetail from dataMap
		Optional<MessageDetail> messageDetail = Optional
				.ofNullable((MessageDetail) dataMap.get(jobDetail.getKey().getName()));

		log.info("Current Job Execution .." + jobDetail.getKey().getGroup());


		// Performs a check up if MessageDetail contains the right object
		// Before emails are sent.
		// @formatter:off
		messageDetail.ifPresentOrElse(msgDetail -> {
			emailService.sendSimpleMessage(msgDetail.getTo(), msgDetail.getSubject(), msgDetail.getMessage(),
					msgDetail.getCc());
		}, () -> {
			log.info("MessageDetail is empty");
			log.info("Job Detail key " + jobDetail.getKey());
		});
		// @formatter:on

	}

}
