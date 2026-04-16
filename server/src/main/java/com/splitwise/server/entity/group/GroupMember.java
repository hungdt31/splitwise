package com.splitwise.server.entity.group;

import com.splitwise.server.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "group_members")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(GroupMember.GroupMemberId.class)
public class GroupMember {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 20)
    @Builder.Default
    private String role = "MEMBER"; // ADMIN | MEMBER

    @Column(name = "joined_at")
    @Builder.Default
    private OffsetDateTime joinedAt = OffsetDateTime.now();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GroupMemberId implements Serializable {
        private UUID group;
        private UUID user;
    }
}
