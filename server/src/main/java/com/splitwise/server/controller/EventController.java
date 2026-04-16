package com.splitwise.server.controller;

import com.splitwise.server.common.ApiResponse;
import com.splitwise.server.controller.support.StubResponseSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * EPIC 2–3 — sự kiện, chi phí, chia tiền, hoạt động (khung).
 */
@RestController
@RequestMapping("/events")
public class EventController implements StubResponseSupport {

    @GetMapping
    public ResponseEntity<ApiResponse<Object>> list(
            @AuthenticationPrincipal String userId,
            @RequestParam(required = false) Boolean upcoming,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) UUID groupId,
            @RequestParam(required = false) String status
    ) {
        return notImplemented("GET /events — US-020, US-021");
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> create(@AuthenticationPrincipal String userId) {
        return notImplemented("POST /events — US-013");
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<ApiResponse<Object>> patch(
            @AuthenticationPrincipal String userId,
            @PathVariable UUID eventId
    ) {
        return notImplemented("PATCH /events/{id} — US-015");
    }

    @PatchMapping("/{eventId}/status")
    public ResponseEntity<ApiResponse<Object>> patchStatus(
            @AuthenticationPrincipal String userId,
            @PathVariable UUID eventId
    ) {
        return notImplemented("PATCH /events/{id}/status — US-016");
    }

    @GetMapping("/{eventId}/participants")
    public ResponseEntity<ApiResponse<Object>> listParticipants(
            @AuthenticationPrincipal String userId,
            @PathVariable UUID eventId
    ) {
        return notImplemented("GET /events/{id}/participants — US-019");
    }

    @PostMapping("/{eventId}/participants")
    public ResponseEntity<ApiResponse<Object>> addParticipants(
            @AuthenticationPrincipal String userId,
            @PathVariable UUID eventId
    ) {
        return notImplemented("POST /events/{id}/participants — US-017");
    }

    @PatchMapping("/{eventId}/participants/me")
    public ResponseEntity<ApiResponse<Object>> rsvpMe(
            @AuthenticationPrincipal String userId,
            @PathVariable UUID eventId
    ) {
        return notImplemented("PATCH /events/{id}/participants/me — US-018");
    }

    @GetMapping("/{eventId}/expenses")
    public ResponseEntity<ApiResponse<Object>> listExpenses(
            @AuthenticationPrincipal String userId,
            @PathVariable UUID eventId
    ) {
        return notImplemented("GET /events/{id}/expenses — US-026");
    }

    @PostMapping("/{eventId}/expenses")
    public ResponseEntity<ApiResponse<Object>> addExpense(
            @AuthenticationPrincipal String userId,
            @PathVariable UUID eventId
    ) {
        return notImplemented("POST /events/{id}/expenses — US-023");
    }

    @GetMapping("/{eventId}/split-summary")
    public ResponseEntity<ApiResponse<Object>> splitSummary(
            @AuthenticationPrincipal String userId,
            @PathVariable UUID eventId,
            @RequestParam(required = false) String strategy
    ) {
        return notImplemented("GET /events/{id}/split-summary — US-027..030");
    }

    @GetMapping("/{eventId}/debt-summary")
    public ResponseEntity<ApiResponse<Object>> debtSummary(
            @AuthenticationPrincipal String userId,
            @PathVariable UUID eventId
    ) {
        return notImplemented("GET /events/{id}/debt-summary — US-031");
    }

    @GetMapping("/{eventId}/payments")
    public ResponseEntity<ApiResponse<Object>> listPayments(
            @AuthenticationPrincipal String userId,
            @PathVariable UUID eventId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) String status
    ) {
        return notImplemented("GET /events/{id}/payments — US-036");
    }

    @GetMapping("/{eventId}/balance/me")
    public ResponseEntity<ApiResponse<Object>> myEventBalance(
            @AuthenticationPrincipal String userId,
            @PathVariable UUID eventId
    ) {
        return notImplemented("GET /events/{id}/balance/me — US-037");
    }

    @GetMapping("/{eventId}/activities")
    public ResponseEntity<ApiResponse<Object>> activities(
            @AuthenticationPrincipal String userId,
            @PathVariable UUID eventId,
            @RequestParam(required = false) Integer page
    ) {
        return notImplemented("GET /events/{id}/activities — US-041");
    }

    @GetMapping("/{eventId}/export")
    public ResponseEntity<ApiResponse<Object>> export(
            @AuthenticationPrincipal String userId,
            @PathVariable UUID eventId,
            @RequestParam(required = false) String format
    ) {
        return notImplemented("GET /events/{id}/export — US-046");
    }
}
