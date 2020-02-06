package com.scheduler.messaging.service;

import org.quartz.SchedulerException;

import com.scheduler.messaging.dto.MessageDetail;

import java.util.List;

/**
 * @author SARFO PHILIP
 */
public interface JobService {

	/**
	 * Method for scheduling jobs to the scheduler
	 * @param messageDetail
	 * @throws SchedulerException
	 */
	void schedule(MessageDetail messageDetail) throws SchedulerException;

	/**
	 * Checks if scheduler instance is running
	 * @return
	 * @throws SchedulerException
	 */
	boolean isRunning() throws SchedulerException;

	/** 
	 * Returns a list of schedule jobs
	 * @return {@link List}
	 * @throws SchedulerException
	 */
	List<String> jobGroupNames() throws SchedulerException;

	/**
	 * Pauses all jobs pending to execute
	 * @throws SchedulerException
	 */
	void pauseAll() throws SchedulerException;

	/**
	 * Pauses job with the key and a group
	 * @param key
	 * @param group
	 * @throws SchedulerException
	 */
	void pauseSJob(String key,String group) throws SchedulerException;

	/**
	 * Resumes all jobs in the scheduler
	 * @throws SchedulerException
	 */
	void resume() throws SchedulerException;


	/**
	 * Resumes a job in the scheduler
	 * @param key
	 * @param group
	 * @throws SchedulerException
	 */
	void resumeJob(String key,String group) throws SchedulerException;
	
	/**
	 * Searches for a job in the scheduler 
	 * @param key
	 * @param group
	 * @return {@link MessageDetail}
	 * @throws SchedulerException
	 */
	MessageDetail findJob(String key,String group) throws SchedulerException;

}
