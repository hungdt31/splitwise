package com.splitwise.server.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Cấu trúc response chuẩn cho toàn bộ API.
 * data = null sẽ bị ẩn khỏi JSON để response gọn hơn.
 */
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private final int status;
    private final String message;
    private final T data;

    @Builder.Default
    private final String timestamp = LocalDateTime.now().toString();

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .status(200)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(T data) {
        return success("Success", data);
    }

    public static ApiResponse<Void> error(int status, String message) {
        return ApiResponse.<Void>builder()
                .status(status)
                .message(message)
                .build();
    }

    /**
     * Dùng cho các endpoint khung (501) — map với backlog trong EventMate_Spec.
     */
    public static <T> ApiResponse<T> stub(String hint) {
        return ApiResponse.<T>builder()
                .status(501)
                .message("Khung API EventMate — " + hint)
                .build();
    }
}
