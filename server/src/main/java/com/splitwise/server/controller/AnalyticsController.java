package com.splitwise.server.controller;

import com.splitwise.server.common.ApiResponse;
import com.splitwise.server.controller.support.StubResponseSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * EPIC 6 — phân tích (khung).
 */
@RestController
@RequestMapping("/analytics")
public class AnalyticsController implements StubResponseSupport {

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<Object>> me(
            @AuthenticationPrincipal String userId,
            @RequestParam(required = false) String period,
            @RequestParam(required = false) Integer year
    ) {
        return notImplemented("GET /analytics/me — US-043");
    }

    @GetMapping("/me/by-category")
    public ResponseEntity<ApiResponse<Object>> byCategory(@AuthenticationPrincipal String userId) {
        return notImplemented("GET /analytics/me/by-category — US-044");
    }

    @GetMapping("/groups/{groupId}/members-spending")
    public ResponseEntity<ApiResponse<Object>> groupMemberSpending(
            @AuthenticationPrincipal String userId,
            @PathVariable UUID groupId
    ) {
        return notImplemented("GET /analytics/groups/{id}/members-spending — US-045");
    }
}
