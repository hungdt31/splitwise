package com.splitwise.server.service.event;

import com.splitwise.server.entity.event.Event;
import com.splitwise.server.entity.event.EventParticipant;
import com.splitwise.server.entity.group.Group;
import com.splitwise.server.entity.user.User;
import com.splitwise.server.repository.event.EventParticipantRepository;
import com.splitwise.server.repository.event.EventRepository;
import com.splitwise.server.repository.group.GroupRepository;
import com.splitwise.server.repository.user.UserRepository;
import com.splitwise.server.exception.ResourceNotFoundException;
import com.splitwise.server.service.event.dto.CreateEventRequest;
import com.splitwise.server.service.event.dto.UpdateEventRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventParticipantRepository eventParticipantRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    /**
     * US-013: Tạo sự kiện mới
     */
    @Transactional
    public Event createEvent(UUID creatorId, CreateEventRequest request) {
        User creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng không tồn tại."));

        Group group = null;
        if (request.getGroupId() != null) {
            group = groupRepository.findById(request.getGroupId())
                    .orElseThrow(() -> new ResourceNotFoundException("Nhóm không tồn tại."));
        }

        Event event = Event.builder()
                .group(group)
                .name(request.getName())
                .description(request.getDescription())
                .category(request.getCategory())
                .location(request.getLocation())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .createdBy(creator)
                .build();
        event = eventRepository.save(event);

        // Tự động thêm người tạo làm participant GOING
        EventParticipant participant = EventParticipant.builder()
                .event(event)
                .user(creator)
                .rsvpStatus("GOING")
                .build();
        eventParticipantRepository.save(participant);

        return event;
    }

    /**
     * US-014 + US-015: Cập nhật sự kiện
     */
    @Transactional
    public Event updateEvent(UUID eventId, UUID requesterId, UpdateEventRequest request) {
        Event event = getActiveEventOrThrow(eventId);
        // TODO: Kiểm tra quyền (chỉ người tạo hoặc ADMIN nhóm mới sửa được)
        event.setName(request.getName());
        event.setDescription(request.getDescription());
        event.setCategory(request.getCategory());
        event.setLocation(request.getLocation());
        event.setStartTime(request.getStartTime());
        event.setEndTime(request.getEndTime());
        return eventRepository.save(event);
    }

    /**
     * US-018: Xoá mềm sự kiện
     */
    @Transactional
    public void deleteEvent(UUID eventId, UUID requesterId) {
        Event event = getActiveEventOrThrow(eventId);
        event.setDeletedAt(OffsetDateTime.now());
        event.setStatus("CANCELLED");
        eventRepository.save(event);
    }

    /**
     * US-019: Cập nhật RSVP
     */
    @Transactional
    public EventParticipant updateRsvp(UUID eventId, UUID userId, String rsvpStatus) {
        EventParticipant participant = eventParticipantRepository
                .findByEventIdAndUserId(eventId, userId)
                .orElseGet(() -> {
                    Event event = getActiveEventOrThrow(eventId);
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new ResourceNotFoundException("Người dùng không tồn tại."));
                    return EventParticipant.builder().event(event).user(user).build();
                });
        participant.setRsvpStatus(rsvpStatus);
        return eventParticipantRepository.save(participant);
    }

    public List<Event> getEventsByGroup(UUID groupId) {
        return eventRepository.findAllByGroupId(groupId);
    }

    public Event getEventById(UUID eventId) {
        return getActiveEventOrThrow(eventId);
    }

    private Event getActiveEventOrThrow(UUID eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Sự kiện không tồn tại."));
        if (event.getDeletedAt() != null) {
            throw new ResourceNotFoundException("Sự kiện đã bị xoá.");
        }
        return event;
    }
}
