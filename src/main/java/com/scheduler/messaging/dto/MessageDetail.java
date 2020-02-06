package com.scheduler.messaging.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Builder
@Data
public class MessageDetail {

    private String messageId;

    private String from;

    private List<String> to;
    
    private String subject;
    
    private List<String> cc;

    private String message;

    private Schedule schedule;

}

