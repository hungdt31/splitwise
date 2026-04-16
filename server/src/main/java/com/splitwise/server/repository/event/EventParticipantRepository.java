package com.splitwise.server.repository.event;

import com.splitwise.server.entity.event.EventParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventParticipantRepository extends JpaRepository<EventParticipant, EventParticipant.EventParticipantId> {

    List<EventParticipant> findAllByEventId(UUID eventId);

    Optional<EventParticipant> findByEventIdAndUserId(UUID eventId, UUID userId);
}
