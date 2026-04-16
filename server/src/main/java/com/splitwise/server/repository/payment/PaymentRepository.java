package com.splitwise.server.repository.payment;

import com.splitwise.server.entity.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    List<Payment> findAllByEventIdOrderByCreatedAtDesc(UUID eventId);

    List<Payment> findAllByFromUserIdOrToUserId(UUID fromUserId, UUID toUserId);
}
