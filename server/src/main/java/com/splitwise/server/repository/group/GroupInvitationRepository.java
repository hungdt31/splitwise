package com.splitwise.server.repository.group;

import com.splitwise.server.entity.group.GroupInvitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupInvitationRepository extends JpaRepository<GroupInvitation, UUID> {

    Optional<GroupInvitation> findByToken(String token);
}
