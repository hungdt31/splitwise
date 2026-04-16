package com.splitwise.server.controller;

import com.splitwise.server.common.ApiResponse;
import com.splitwise.server.controller.support.StubResponseSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * EPIC 1 — nhóm (khung).
 */
@RestController
@RequestMapping("/groups")
public class GroupController implements StubResponseSupport {

    @GetMapping
    public ResponseEntity<ApiResponse<Object>> list(
            @AuthenticationPrincipal String userId,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String sort
    ) {
        return notImplemented("GET /groups — US-012");
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> create(@AuthenticationPrincipal String userId) {
        return notImplemented("POST /groups — US-008");
    }

    @PostMapping("/{groupId}/invitations")
    public ResponseEntity<ApiResponse<Object>> invite(
            @AuthenticationPrincipal String userId,
            @PathVariable UUID groupId
    ) {
        return notImplemented("POST /groups/{id}/invitations — US-009");
    }

    @GetMapping("/{groupId}/members")
    public ResponseEntity<ApiResponse<Object>> listMembers(
            @AuthenticationPrincipal String userId,
            @PathVariable UUID groupId
    ) {
        return notImplemented("GET /groups/{id}/members — US-010");
    }

    @PatchMapping("/{groupId}/members/{memberId}")
    public ResponseEntity<ApiResponse<Object>> patchMember(
            @AuthenticationPrincipal String userId,
            @PathVariable UUID groupId,
            @PathVariable UUID memberId
    ) {
        return notImplemented("PATCH /groups/{id}/members/{memberId} — US-010");
    }

    @DeleteMapping("/{groupId}/members/{memberId}")
    public ResponseEntity<ApiResponse<Object>> removeMember(
            @AuthenticationPrincipal String userId,
            @PathVariable UUID groupId,
            @PathVariable UUID memberId
    ) {
        return notImplemented("DELETE /groups/{id}/members/{memberId} — US-010");
    }

    @DeleteMapping("/{groupId}/members/me")
    public ResponseEntity<ApiResponse<Object>> leaveGroup(
            @AuthenticationPrincipal String userId,
            @PathVariable UUID groupId
    ) {
        return notImplemented("DELETE /groups/{id}/members/me — US-011");
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<ApiResponse<Object>> dissolve(
            @AuthenticationPrincipal String userId,
            @PathVariable UUID groupId
    ) {
        return notImplemented("DELETE /groups/{id} — US-011 (admin)");
    }
}
