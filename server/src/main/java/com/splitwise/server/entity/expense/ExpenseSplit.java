package com.splitwise.server.entity.expense;

import com.splitwise.server.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "expense_splits")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseSplit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expense_id", nullable = false)
    private Expense expense;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "share_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal shareAmount;

    /**
     * EQUAL | PERCENTAGE | CUSTOM_AMOUNT
     */
    @Column(name = "split_strategy", nullable = false, length = 30)
    private String splitStrategy;

    @Column(precision = 5, scale = 2)
    private BigDecimal percentage; // only when splitStrategy = PERCENTAGE

    @Column(name = "created_at", updatable = false)
    @Builder.Default
    private OffsetDateTime createdAt = OffsetDateTime.now();
}
