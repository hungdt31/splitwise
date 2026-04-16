package com.splitwise.server.service.expense.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CreateExpenseRequest {

    @NotNull
    private UUID eventId;

    @NotNull
    private UUID payerId;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private String title;

    private String category;

    @NotNull
    private LocalDate expenseDate;

    private String description;
    private List<UUID> includedParticipants;

    @NotNull
    private SplitRequest split;
}
