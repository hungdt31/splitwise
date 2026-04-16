package com.splitwise.server.service.expense;

import com.splitwise.server.entity.event.Event;
import com.splitwise.server.entity.event.EventParticipant;
import com.splitwise.server.entity.expense.Debt;
import com.splitwise.server.entity.expense.Expense;
import com.splitwise.server.entity.expense.ExpenseSplit;
import com.splitwise.server.entity.user.User;
import com.splitwise.server.repository.event.EventParticipantRepository;
import com.splitwise.server.repository.event.EventRepository;
import com.splitwise.server.repository.expense.DebtRepository;
import com.splitwise.server.repository.expense.ExpenseRepository;
import com.splitwise.server.repository.expense.ExpenseSplitRepository;
import com.splitwise.server.repository.user.UserRepository;
import com.splitwise.server.exception.ResourceNotFoundException;
import com.splitwise.server.service.expense.dto.CreateExpenseRequest;
import com.splitwise.server.service.expense.dto.SplitRequest;
import com.splitwise.server.service.expense.dto.DebtSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseSplitRepository expenseSplitRepository;
    private final DebtRepository debtRepository;
    private final EventRepository eventRepository;
    private final EventParticipantRepository eventParticipantRepository;
    private final UserRepository userRepository;

    /**
     * US-023: Thêm chi phí mới
     */
    @Transactional
    public Expense createExpense(UUID actorId, CreateExpenseRequest request) {
        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Sự kiện không tồn tại."));
        User payer = userRepository.findById(request.getPayerId())
                .orElseThrow(() -> new ResourceNotFoundException("Người trả không tồn tại."));
        User createdBy = userRepository.findById(actorId)
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng không tồn tại."));

        Expense expense = Expense.builder()
                .event(event)
                .payer(payer)
                .amount(request.getAmount())
                .title(request.getTitle())
                .category(request.getCategory())
                .expenseDate(request.getExpenseDate())
                .description(request.getDescription())
                .includedParticipants(request.getIncludedParticipants())
                .createdBy(createdBy)
                .build();
        expense = expenseRepository.save(expense);

        // Tính split và cập nhật debts
        computeSplitsAndDebts(expense, request.getSplit());
        return expense;
    }

    /**
     * US-025: Xoá expense (chỉ người tạo, chưa có payment liên quan)
     */
    @Transactional
    public void deleteExpense(UUID expenseId, UUID requesterId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Chi phí không tồn tại."));
        // TODO: Guard - không xoá nếu đã có payment liên quan
        expenseRepository.delete(expense);
    }

    /**
     * US-026: Lấy danh sách chi phí theo event
     */
    public List<Expense> getExpensesByEvent(UUID eventId) {
        return expenseRepository.findAllByEventIdOrderByExpenseDateDesc(eventId);
    }

    /**
     * US-031: Lấy debt summary (ai nợ ai bao nhiêu)
     */
    public List<DebtSummaryResponse> getDebtSummary(UUID eventId) {
        return debtRepository.findAllByEventId(eventId).stream()
                .map(d -> DebtSummaryResponse.builder()
                        .debtId(d.getId())
                        .fromUserId(d.getFromUser().getId())
                        .fromUserName(d.getFromUser().getFullName())
                        .toUserId(d.getToUser().getId())
                        .toUserName(d.getToUser().getFullName())
                        .amount(d.getAmount())
                        .status(d.getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    // =============================================
    // Internal: Tính toán chia tiền & cập nhật debts
    // =============================================

    private void computeSplitsAndDebts(Expense expense, SplitRequest splitRequest) {
        List<UUID> participantIds = resolveParticipantIds(expense);

        List<ExpenseSplit> splits = switch (splitRequest.getStrategy()) {
            case "EQUAL" -> splitEqual(expense, participantIds);
            case "PERCENTAGE" -> splitByPercentage(expense, participantIds, splitRequest.getPercentages());
            case "CUSTOM_AMOUNT" -> splitByCustomAmount(expense, splitRequest.getCustomAmounts());
            default -> throw new IllegalArgumentException("Chiến lược chia tiền không hợp lệ.");
        };

        expenseSplitRepository.saveAll(splits);
        updateDebts(expense, splits);
    }

    /**
     * US-027: EQUAL — chia đều, phần dư cộng cho payer
     */
    private List<ExpenseSplit> splitEqual(Expense expense, List<UUID> participantIds) {
        int n = participantIds.size();
        BigDecimal base = expense.getAmount().divide(BigDecimal.valueOf(n), 2, RoundingMode.DOWN);
        BigDecimal remainder = expense.getAmount().subtract(base.multiply(BigDecimal.valueOf(n)));

        List<ExpenseSplit> splits = new ArrayList<>();
        UUID payerId = expense.getPayer().getId();

        for (UUID userId : participantIds) {
            User user = userRepository.getReferenceById(userId);
            BigDecimal share = userId.equals(payerId) ? base.add(remainder) : base;
            splits.add(ExpenseSplit.builder()
                    .expense(expense)
                    .user(user)
                    .shareAmount(share)
                    .splitStrategy("EQUAL")
                    .build());
        }
        return splits;
    }

    /**
     * US-028: PERCENTAGE
     */
    private List<ExpenseSplit> splitByPercentage(Expense expense, List<UUID> participantIds,
                                                  Map<UUID, BigDecimal> percentages) {
        BigDecimal total = percentages.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        if (total.compareTo(BigDecimal.valueOf(100)) != 0) {
            throw new IllegalArgumentException("Tổng phần trăm phải bằng 100.");
        }

        List<ExpenseSplit> splits = new ArrayList<>();
        for (UUID userId : participantIds) {
            BigDecimal pct = percentages.getOrDefault(userId, BigDecimal.ZERO);
            BigDecimal share = expense.getAmount().multiply(pct)
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            splits.add(ExpenseSplit.builder()
                    .expense(expense)
                    .user(userRepository.getReferenceById(userId))
                    .shareAmount(share)
                    .splitStrategy("PERCENTAGE")
                    .percentage(pct)
                    .build());
        }
        return splits;
    }

    /**
     * US-029: CUSTOM_AMOUNT
     */
    private List<ExpenseSplit> splitByCustomAmount(Expense expense, Map<UUID, BigDecimal> customAmounts) {
        BigDecimal total = customAmounts.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        if (total.compareTo(expense.getAmount()) != 0) {
            throw new IllegalArgumentException("Tổng số tiền tùy chỉnh phải bằng tổng chi phí.");
        }

        return customAmounts.entrySet().stream()
                .map(e -> ExpenseSplit.builder()
                        .expense(expense)
                        .user(userRepository.getReferenceById(e.getKey()))
                        .shareAmount(e.getValue())
                        .splitStrategy("CUSTOM_AMOUNT")
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * Cập nhật bảng debts sau khi tính split.
     * Payer đã trả toàn bộ → các người khác nợ payer phần của họ.
     */
    private void updateDebts(Expense expense, List<ExpenseSplit> splits) {
        UUID payerId = expense.getPayer().getId();
        UUID eventId = expense.getEvent().getId();

        for (ExpenseSplit split : splits) {
            UUID debtorId = split.getUser().getId();
            if (debtorId.equals(payerId)) continue; // payer không nợ chính mình

            Optional<Debt> existing = debtRepository.findByEvent_IdAndFromUser_IdAndToUser_Id(
                    eventId, debtorId, payerId);

            if (existing.isPresent()) {
                Debt debt = existing.get();
                debt.setAmount(debt.getAmount().add(split.getShareAmount()));
                debtRepository.save(debt);
            } else {
                debtRepository.save(Debt.builder()
                        .event(expense.getEvent())
                        .fromUser(split.getUser())
                        .toUser(expense.getPayer())
                        .amount(split.getShareAmount())
                        .build());
            }
        }
    }

    private List<UUID> resolveParticipantIds(Expense expense) {
        if (expense.getIncludedParticipants() != null && !expense.getIncludedParticipants().isEmpty()) {
            return expense.getIncludedParticipants();
        }
        return eventParticipantRepository.findAllByEventId(expense.getEvent().getId())
                .stream().map(ep -> ep.getUser().getId()).collect(Collectors.toList());
    }
}
