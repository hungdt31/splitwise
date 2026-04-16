package com.splitwise.server.controller;

import com.splitwise.server.common.ApiResponse;
import com.splitwise.server.controller.support.StubResponseSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * EPIC 1 — profile & user search (scaffold).
 */
@RestController
@RequestMapping("/users")
public class UserController implements StubResponseSupport {

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<Object>> getMe(@AuthenticationPrincipal String userId) {
        return notImplemented("GET /users/me — US-005, US-007 (principal=" + userId + ")");
    }

    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<Object>> patchMe(@AuthenticationPrincipal String userId) {
        return notImplemented("PATCH /users/me — US-005");
    }

    @GetMapping("/me/events")
    public ResponseEntity<ApiResponse<Object>> myEvents(
            @AuthenticationPrincipal String userId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit
    ) {
        return notImplemented("GET /users/me/events — US-006");
    }

    @GetMapping("/me/balance-summary")
    public ResponseEntity<ApiResponse<Object>> balanceSummary(@AuthenticationPrincipal String userId) {
        return notImplemented("GET /users/me/balance-summary — US-007");
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Object>> search(@RequestParam String q) {
        return notImplemented("GET /users/search — US-009 (q)");
    }

    @PatchMapping("/me/notification-preferences")
    public ResponseEntity<ApiResponse<Object>> notificationPreferences(@AuthenticationPrincipal String userId) {
        return notImplemented("PATCH /users/me/notification-preferences — US-040");
    }
}
