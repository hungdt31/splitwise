package com.splitwise.server.entity.group;

import com.splitwise.server.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "group_invitations")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupInvitation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invited_by", nullable = false)
    private User invitedBy;

    @Column(length = 255)
    private String email;

    @Column(unique = true, nullable = false, length = 100)
    private String token;

    @Column(name = "expires_at", nullable = false)
    private OffsetDateTime expiresAt;

    /**
     * PENDING | ACCEPTED | EXPIRED | CANCELLED
     */
    @Column(length = 20)
    @Builder.Default
    private String status = "PENDING";

    @Column(name = "created_at", updatable = false)
    @Builder.Default
    private OffsetDateTime createdAt = OffsetDateTime.now();
}
