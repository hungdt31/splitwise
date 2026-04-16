package com.splitwise.server.controller;

import com.splitwise.server.common.ApiResponse;
import com.splitwise.server.controller.support.StubResponseSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Chi phí độc lập theo spec PATCH/DELETE /expenses/:id — US-025.
 */
@RestController
@RequestMapping("/expenses")
public class ExpenseController implements StubResponseSupport {

    @PatchMapping("/{expenseId}")
    public ResponseEntity<ApiResponse<Object>> patch(
            @AuthenticationPrincipal String userId,
            @PathVariable UUID expenseId
    ) {
        return notImplemented("PATCH /expenses/{id} — US-025");
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<ApiResponse<Object>> delete(
            @AuthenticationPrincipal String userId,
            @PathVariable UUID expenseId
    ) {
        return notImplemented("DELETE /expenses/{id} — US-025");
    }
}
