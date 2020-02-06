package com.scheduler.messaging.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.scheduler.messaging.dto.MessageDetail;
import com.scheduler.messaging.dto.Schedule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig(name = "")
@ContextConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
class ScheduleResourceTest {

    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wab;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wab).build();
    }


    private String requestJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
        ObjectWriter writer = objectMapper.writer().withDefaultPrettyPrinter();
        return writer.writeValueAsString(object);
    }



    @Test
    void schedule() throws Exception {
    	MessageDetail messageDetail = MessageDetail.builder()
                .messageId(UUID.randomUUID().toString())
                .to(addressList())
                .from("powusu@gmail.com")
                .message("Lorem Ipsum testing")
                .schedule(Schedule.builder()
                		.scheduleName("Email Compaign")
                		.scheduleDate(LocalDateTime.now().plusDays(1).toString())
                		.build())
                .build();
    	
        String messageDetailStr = requestJson(messageDetail);
        // formatter: off
            mockMvc.perform(post("/v0.01/schedule")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(messageDetailStr))
                    .andExpect(status().isAccepted());
        // formatter: on
    }
    
    private List<String> addressList(){
    	List<String> to = new ArrayList<String>();
    	to.add("philsarfogh@gmail.com");
    	return to;
    }


}