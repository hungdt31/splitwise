package com.splitwise.server.service.group.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateGroupRequest {

    @NotBlank
    @Size(min = 2, max = 50)
    private String name;

    private String description;
    private String coverImageUrl;
}
