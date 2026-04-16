package com.splitwise.server.repository.group;

import com.splitwise.server.entity.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {

    @Query("SELECT gm.group FROM GroupMember gm WHERE gm.user.id = :userId AND gm.group.status <> 'DELETED'")
    List<Group> findAllByMember(UUID userId);
}
