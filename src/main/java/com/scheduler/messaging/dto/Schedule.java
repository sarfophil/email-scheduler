package com.scheduler.messaging.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@ToString
@Builder
@Data
public class Schedule{
    private String scheduleName;

    private String scheduleDate;

    private boolean isExecuted;
    
    public ZonedDateTime scheduleDateConverter(){
        return ZonedDateTime.of(LocalDateTime.parse(scheduleDate), ZoneId.systemDefault());
    }
}