package com.scheduler.messaging.service.impl;

import com.scheduler.messaging.dto.MessageDetail;
import com.scheduler.messaging.dto.Schedule;
import com.scheduler.messaging.service.JobService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitWebConfig(name = "")
@ContextConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
class JobServiceImplTest {

    @Autowired
    private JobService jobService;
    
    @Autowired
    private Scheduler startSchedulerFactory;

    MessageDetail messageDetail;

    @BeforeEach
    public void setup() throws SchedulerException {
    	
        messageDetail = MessageDetail.builder()
                .messageId(UUID.randomUUID().toString())
                .to(addressList())
                .from("powusu@gmail.com")
                .message("Lorem Ipsum testing")
                .schedule(Schedule.builder()
                		.scheduleName("Email Compaign")
                		.scheduleDate(LocalDateTime.now().plusDays(1).toString())
                		.build())
                .build();


       // jobService.schedule(messageDetail);
    }
    
    private List<String> addressList(){
    	List<String> to = new ArrayList<String>();
    	to.add("philsarfogh@gmail.com");
    	return to;
    }

    @Test
    void schedule() throws SchedulerException {
    }

    @Test
    void jobs() throws SchedulerException {
        assertThat(jobService.jobGroupNames()).isNotNull();
    }

    @Test
    void isRunning() throws SchedulerException {
        assertThat(jobService.isRunning()).isTrue();
    }
    
    @Test
    void findJobKey() throws SchedulerException {
    	String key = "bc89c518-481a-4f1c-b68d-3d0854a16fab";
    	Set<JobKey> jobKey = startSchedulerFactory.getJobKeys(GroupMatcher.jobGroupEquals(key));
    	assertThat(GroupMatcher.jobGroupEquals(key)).descriptionText();
    }
    
    //@Test
    void findJob() throws SchedulerException {
    	String key = "bc89c518-481a-4f1c-b68d-3d0854a16fab";
    	assertThat(jobService.findJob(key,"")).isNotNull();
    }
}