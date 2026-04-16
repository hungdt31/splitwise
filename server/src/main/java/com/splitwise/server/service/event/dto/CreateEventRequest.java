package com.splitwise.server.service.event.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class CreateEventRequest {

    private UUID groupId;

    @NotBlank
    private String name;

    private String description;
    private String category;
    private String location;
    private BigDecimal latitude;
    private BigDecimal longitude;

    @NotNull
    private OffsetDateTime startTime;

    private OffsetDateTime endTime;
}
