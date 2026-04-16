package com.splitwise.server.controller;

import com.splitwise.server.common.ApiResponse;
import com.splitwise.server.controller.support.StubResponseSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * EPIC 4 — thanh toán (khung).
 */
@RestController
@RequestMapping("/payments")
public class PaymentController implements StubResponseSupport {

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> record(@AuthenticationPrincipal String userId) {
        return notImplemented("POST /payments — US-032");
    }

    @PatchMapping("/{paymentId}/confirm")
    public ResponseEntity<ApiResponse<Object>> confirm(
            @AuthenticationPrincipal String userId,
            @PathVariable UUID paymentId
    ) {
        return notImplemented("PATCH /payments/{id}/confirm — US-033");
    }

    @PatchMapping("/{paymentId}/reject")
    public ResponseEntity<ApiResponse<Object>> reject(
            @AuthenticationPrincipal String userId,
            @PathVariable UUID paymentId
    ) {
        return notImplemented("PATCH /payments/{id}/reject — US-034");
    }
}
