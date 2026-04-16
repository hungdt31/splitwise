package com.splitwise.server.repository.expense;

import com.splitwise.server.entity.expense.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

    List<Expense> findAllByEventIdOrderByExpenseDateDesc(UUID eventId);
}
