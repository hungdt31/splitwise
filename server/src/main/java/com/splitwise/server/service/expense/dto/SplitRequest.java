package com.splitwise.server.service.expense.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

/**
 * Chiến lược chia: EQUAL | PERCENTAGE | CUSTOM_AMOUNT (US-027..029).
 */
@Data
@Builder
public class SplitRequest {

    /** EQUAL | PERCENTAGE | CUSTOM_AMOUNT */
    private String strategy;

    private Map<UUID, BigDecimal> percentages;
    private Map<UUID, BigDecimal> customAmounts;
}
