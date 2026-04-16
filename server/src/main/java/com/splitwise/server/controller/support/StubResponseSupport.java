package com.splitwise.server.controller.support;

import com.splitwise.server.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Tiện ích trả 501 cho các endpoint khung (map backlog EventMate).
 */
public interface StubResponseSupport {

    default <T> ResponseEntity<ApiResponse<T>> notImplemented(String backlogHint) {
        return ResponseEntity
                .status(HttpStatus.NOT_IMPLEMENTED)
                .body(ApiResponse.stub(backlogHint));
    }
}
