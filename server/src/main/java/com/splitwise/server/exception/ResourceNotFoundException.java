package com.splitwise.server.exception;

/**
 * Ném ra khi không tìm thấy tài nguyên trong database.
 * Ví dụ: throw new ResourceNotFoundException("Người dùng không tồn tại với id: " + id);
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public static ResourceNotFoundException of(String resource, Object id) {
        return new ResourceNotFoundException(resource + " không tồn tại với id: " + id);
    }
}
