package com.splitwise.server.entity.event;

import com.splitwise.server.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "event_participants")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(EventParticipant.EventParticipantId.class)
public class EventParticipant {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "rsvp_status", length = 20)
    @Builder.Default
    private String rsvpStatus = "NO_RESPONSE"; // GOING | MAYBE | NOT_GOING | NO_RESPONSE

    @Column(name = "joined_at")
    @Builder.Default
    private OffsetDateTime joinedAt = OffsetDateTime.now();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventParticipantId implements Serializable {
        private UUID event;
        private UUID user;
    }
}
