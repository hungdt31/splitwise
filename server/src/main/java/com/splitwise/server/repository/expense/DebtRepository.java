package com.splitwise.server.repository.expense;

import com.splitwise.server.entity.expense.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DebtRepository extends JpaRepository<Debt, UUID> {

    List<Debt> findAllByEventId(UUID eventId);

    Optional<Debt> findByEvent_IdAndFromUser_IdAndToUser_Id(UUID eventId, UUID fromUserId, UUID toUserId);

    List<Debt> findAllByFromUserIdAndStatus(UUID fromUserId, String status);

    List<Debt> findAllByToUserIdAndStatus(UUID toUserId, String status);
}
