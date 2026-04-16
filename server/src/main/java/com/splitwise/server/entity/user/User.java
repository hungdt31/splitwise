package com.splitwise.server.entity.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "password_hash", length = 255)
    private String passwordHash;

    @Column(name = "google_id", unique = true, length = 255)
    private String googleId;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "avatar_url", columnDefinition = "TEXT")
    private String avatarUrl;

    /**
     * JSONB: { bankName, accountNumber, accountName }
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "bank_account_info", columnDefinition = "jsonb")
    private Map<String, Object> bankAccountInfo;

    /**
     * JSONB: { event, expense, payment, reminder } — booleans
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "notification_preferences", columnDefinition = "jsonb")
    private Map<String, Boolean> notificationPreferences;

    @Column(name = "is_email_verified")
    @Builder.Default
    private Boolean isEmailVerified = false;

    @Column(length = 20)
    @Builder.Default
    private String status = "ACTIVE"; // ACTIVE | INACTIVE | DELETED

    @Column(name = "created_at", updatable = false)
    @Builder.Default
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(name = "updated_at")
    @Builder.Default
    private OffsetDateTime updatedAt = OffsetDateTime.now();
}
