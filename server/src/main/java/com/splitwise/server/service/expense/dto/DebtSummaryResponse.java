package com.splitwise.server.service.expense.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class DebtSummaryResponse {

    private UUID debtId;
    private UUID fromUserId;
    private String fromUserName;
    private UUID toUserId;
    private String toUserName;
    private BigDecimal amount;
    private String status;
}
