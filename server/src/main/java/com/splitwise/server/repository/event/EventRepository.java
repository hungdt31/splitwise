package com.splitwise.server.repository.event;

import com.splitwise.server.entity.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    @Query("SELECT e FROM Event e WHERE e.group.id = :groupId AND e.deletedAt IS NULL ORDER BY e.startTime DESC")
    List<Event> findAllByGroupId(UUID groupId);

    @Query("SELECT ep.event FROM EventParticipant ep WHERE ep.user.id = :userId AND ep.event.deletedAt IS NULL ORDER BY ep.event.startTime DESC")
    List<Event> findAllByParticipantId(UUID userId);
}
