package com.scheduler.messaging.resource;

import com.scheduler.messaging.dto.MessageDetail;
import com.scheduler.messaging.service.JobService;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * 
 * @author SARFO PHILIP
 *
 */

@Slf4j
@RestController
@RequestMapping("/v0.01")
public class ScheduleResource {

	@Autowired
	private JobService jobService;

	@PostMapping("/schedule")
	public ResponseEntity<EntityModel<String>> schedule(@RequestBody MessageDetail messageDetail)
			throws SchedulerException {
		String messageCode = UUID.randomUUID().toString();
		messageDetail.setMessageId(messageCode);
		jobService.schedule(messageDetail);
		return ResponseEntity
				.created(linkTo(methodOn(ScheduleResource.class).getJob(messageCode,
						messageDetail.getSchedule().getScheduleName())).withRel(messageCode).toUri())
				.body(new EntityModel<String>("Email scheduled!!"));
	}
	

	@SuppressWarnings("rawtypes")
	@PostMapping("/schedule/list")
	public ResponseEntity<RepresentationModel> scheduleList(
			@NonNull @RequestBody List<MessageDetail> messageDetailList) {
		RepresentationModel model = new RepresentationModel();
		for (MessageDetail msg : messageDetailList) {
			msg.setMessageId(UUID.randomUUID().toString());
			try {
				jobService.schedule(msg);

				model.add(linkTo(methodOn(ScheduleResource.class).//
						getJob(msg.getSchedule().getScheduleName(), msg.getMessageId()))
								.withRel(msg.getSchedule().getScheduleName()));

			} catch (SchedulerException e) {
				log.info("Unable to schedule messages");

			}
		}

		return ResponseEntity.accepted().body(model);
	}
	
	

	@SuppressWarnings("rawtypes")
	@GetMapping("/jobs")
	public ResponseEntity<CollectionModel> jobGroups() throws SchedulerException {
		return ResponseEntity.ok(//
				new CollectionModel<>(jobService.jobGroupNames()));

	}

	@SuppressWarnings("rawtypes")
	@GetMapping("/jobs/{group}/{key}")
	public ResponseEntity<EntityModel> getJob(@PathVariable("group") String group, @PathVariable("key") String key)
			throws SchedulerException {
		return ResponseEntity.ok(new EntityModel<>(jobService.findJob(key, group)));
	}

	/**
	 * Pause all jobs in the scheduler
	 */
	@PutMapping("/jobs/pause/all")
	public ResponseEntity<?> pauseAll() throws SchedulerException {
		jobService.pauseAll();
		return ResponseEntity.accepted().build();
	}

	/**
	 * Pause a specific job with groupName and jobKey
	 */
	@PutMapping("/job/pause/{group}/{job_key}")
	public ResponseEntity<?> pause(@PathVariable("job_key") String jobKey, @PathVariable("group") String group)
			throws SchedulerException {
		jobService.pauseSJob(jobKey, group);
		return ResponseEntity.accepted().build();
	}

	@PutMapping("/jobs/resume/all")
	public ResponseEntity<?> resume() throws SchedulerException {
		jobService.resume();
		return ResponseEntity.accepted().build();
	}

	@PutMapping("/job/resume/{group}/{job_key}")
	public ResponseEntity<?> resumeJob(@PathVariable("job_key") String jobKey, @PathVariable("group") String group)
			throws SchedulerException {
		jobService.resumeJob(jobKey, group);
		return ResponseEntity.accepted().build();
	}
}
