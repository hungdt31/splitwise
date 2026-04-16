package com.splitwise.server.repository.group;

import com.splitwise.server.entity.group.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMember.GroupMemberId> {

    List<GroupMember> findAllByGroupId(UUID groupId);

    Optional<GroupMember> findByGroupIdAndUserId(UUID groupId, UUID userId);

    boolean existsByGroupIdAndUserId(UUID groupId, UUID userId);

    void deleteByGroupIdAndUserId(UUID groupId, UUID userId);
}
