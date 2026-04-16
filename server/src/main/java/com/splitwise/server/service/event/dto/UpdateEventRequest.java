package com.splitwise.server.service.event.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class UpdateEventRequest {

    private String name;
    private String description;
    private String category;
    private String location;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
}
