package com.splitwise.server.entity.expense;

import com.splitwise.server.entity.event.Event;
import com.splitwise.server.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "expenses")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payer_id", nullable = false)
    private User payer;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 50)
    private String category; // VENUE | FOOD | DRINK | TRANSPORT | OTHER

    @Column(name = "expense_date", nullable = false)
    private LocalDate expenseDate;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "receipt_url", columnDefinition = "TEXT")
    private String receiptUrl;

    /**
     * JSONB array of user UUIDs to include in this expense split.
     * If null → all event participants are included.
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "included_participants", columnDefinition = "jsonb")
    private List<UUID> includedParticipants;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Column(name = "created_at", updatable = false)
    @Builder.Default
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(name = "updated_at")
    @Builder.Default
    private OffsetDateTime updatedAt = OffsetDateTime.now();
}
