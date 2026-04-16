package com.splitwise.server.controller;

import com.splitwise.server.common.ApiResponse;
import com.splitwise.server.controller.support.StubResponseSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * EPIC 5 — thông báo (khung).
 */
@RestController
@RequestMapping("/notifications")
public class NotificationController implements StubResponseSupport {

    @GetMapping("/unread-count")
    public ResponseEntity<ApiResponse<Object>> unreadCount(@AuthenticationPrincipal String userId) {
        return notImplemented("GET /notifications/unread-count — US-042");
    }
}
