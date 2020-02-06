package com.scheduler.messaging.service.impl;

import org.quartz.*;
import static org.quartz.JobBuilder.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scheduler.messaging.dto.MessageDetail;
import com.scheduler.messaging.job.JobExecutor;
import com.scheduler.messaging.service.JobService;


@Service
public class JobServiceImpl implements JobService {

	@Autowired
	private Scheduler startSchedulerFactory;

	@Override
	public boolean isRunning() throws SchedulerException {
		return startSchedulerFactory.isStarted();
	}

	
	@Override
	public void schedule(MessageDetail messageDetail) throws SchedulerException {
		// @formatter:off
		JobDataMap dataMap = new JobDataMap();
		dataMap.put(messageDetail.getMessageId(), messageDetail);

		// adds messageDetail to the JobDataMap
		JobDetail job = newJob(JobExecutor.class) //
				.withIdentity(messageDetail.getMessageId(), messageDetail.getSchedule().getScheduleName())
				.setJobData(dataMap).build();

		// Triggers a job to trigger at a specific event
		Trigger trigger = TriggerBuilder.newTrigger()//
				.forJob(job) //
				.withIdentity(messageDetail.getMessageId(), messageDetail.getSchedule().getScheduleName())
				.startAt(Date.from(messageDetail.getSchedule().scheduleDateConverter().toInstant())) //
				.withSchedule(SimpleScheduleBuilder.simpleSchedule() //
						.withMisfireHandlingInstructionFireNow())
				.build();
		// @formatter:on

		// Schedules Job
		startSchedulerFactory.scheduleJob(job, trigger);

	}

	@Override
	public List<String> jobGroupNames() throws SchedulerException {
		return startSchedulerFactory.getJobGroupNames();
	}

	public MessageDetail findJob(String key, String group) throws SchedulerException {
		JobDetail jobDetail = startSchedulerFactory.getJobDetail(JobKey.jobKey(key, group));
		return Optional.ofNullable((MessageDetail) jobDetail.getJobDataMap().get(jobDetail.getKey().getName()))
				.orElseThrow();
	}

	public void pauseAll() throws SchedulerException {
		startSchedulerFactory.pauseAll();
	}

	public void pauseSJob(String key, String group) throws SchedulerException {
		startSchedulerFactory.pauseJob(JobKey.jobKey(key, group));
	}

	public void resume() throws SchedulerException {
		startSchedulerFactory.resumeAll();
	}

	public void resumeJob(String key, String group) throws SchedulerException {
		startSchedulerFactory.pauseJob(JobKey.jobKey(key, group));
	}

}
