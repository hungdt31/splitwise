package com.splitwise.server.service.group;

import com.splitwise.server.entity.group.Group;
import com.splitwise.server.entity.group.GroupInvitation;
import com.splitwise.server.entity.group.GroupMember;
import com.splitwise.server.entity.user.User;
import com.splitwise.server.repository.group.GroupInvitationRepository;
import com.splitwise.server.repository.group.GroupMemberRepository;
import com.splitwise.server.repository.group.GroupRepository;
import com.splitwise.server.repository.user.UserRepository;
import com.splitwise.server.exception.ResourceNotFoundException;
import com.splitwise.server.service.group.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final GroupInvitationRepository groupInvitationRepository;
    private final UserRepository userRepository;

    /**
     * US-008: Tạo nhóm mới
     */
    @Transactional
    public Group createGroup(UUID creatorId, CreateGroupRequest request) {
        User creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng không tồn tại."));

        Group group = Group.builder()
                .name(request.getName())
                .description(request.getDescription())
                .coverImageUrl(request.getCoverImageUrl())
                .createdBy(creator)
                .build();
        group = groupRepository.save(group);

        // Tự động thêm người tạo làm ADMIN
        GroupMember adminMember = GroupMember.builder()
                .group(group)
                .user(creator)
                .role("ADMIN")
                .build();
        groupMemberRepository.save(adminMember);

        return group;
    }

    /**
     * US-009: Mời thành viên qua token
     */
    @Transactional
    public GroupInvitation createInvitation(UUID groupId, UUID invitedByUserId, String email) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Nhóm không tồn tại."));
        User invitedBy = userRepository.findById(invitedByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng không tồn tại."));

        GroupInvitation invitation = GroupInvitation.builder()
                .group(group)
                .invitedBy(invitedBy)
                .email(email)
                .token(UUID.randomUUID().toString())
                .expiresAt(OffsetDateTime.now().plusDays(7))
                .build();
        return groupInvitationRepository.save(invitation);
    }

    /**
     * US-010: Xem danh sách thành viên
     */
    public List<GroupMember> getMembers(UUID groupId) {
        return groupMemberRepository.findAllByGroupId(groupId);
    }

    /**
     * US-011: Rời khỏi nhóm
     */
    @Transactional
    public void leaveGroup(UUID groupId, UUID userId) {
        // TODO: Guard - không cho rời nếu còn nợ chưa giải quyết
        groupMemberRepository.deleteByGroupIdAndUserId(groupId, userId);
    }

    /**
     * US-011: Giải tán nhóm (chỉ ADMIN)
     */
    @Transactional
    public void dissolveGroup(UUID groupId, UUID requesterId) {
        GroupMember requester = groupMemberRepository.findByGroupIdAndUserId(groupId, requesterId)
                .orElseThrow(() -> new ResourceNotFoundException("Thành viên không tồn tại trong nhóm."));

        if (!"ADMIN".equals(requester.getRole())) {
            throw new IllegalStateException("Chỉ ADMIN mới có thể giải tán nhóm.");
        }
        // TODO: Guard - không cho giải tán nếu còn nợ chưa giải quyết
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Nhóm không tồn tại."));
        group.setStatus("DELETED");
        groupRepository.save(group);
    }

    /**
     * US-012: Lấy danh sách nhóm của user
     */
    public List<Group> getMyGroups(UUID userId) {
        return groupRepository.findAllByMember(userId);
    }
}
