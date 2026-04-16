package com.splitwise.server.service.auth;

import com.splitwise.server.entity.user.User;
import com.splitwise.server.repository.user.UserRepository;
import com.splitwise.server.exception.ResourceNotFoundException;
import com.splitwise.server.service.auth.dto.LoginRequest;
import com.splitwise.server.service.auth.dto.RegisterRequest;
import com.splitwise.server.service.auth.dto.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    /**
     * US-001: Đăng ký bằng email/password
     */
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email đã được sử dụng.");
        }

        User user = User.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .build();
        userRepository.save(user);

        // TODO: Gửi OTP xác thực email (US-001 T3)
        String token = jwtService.generateToken(user.getId().toString());
        return AuthResponse.builder()
                .accessToken(token)
                .userId(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .build();
    }

    /**
     * US-002: Đăng nhập và duy trì session
     */
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng không tồn tại."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Mật khẩu không đúng.");
        }

        String token = jwtService.generateToken(user.getId().toString());
        // TODO: Tạo Refresh Token và lưu vào secure storage phía client (US-002 T2)
        return AuthResponse.builder()
                .accessToken(token)
                .userId(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .build();
    }

    /**
     * US-004: Quên mật khẩu
     */
    public void forgotPassword(String email) {
        userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email không tồn tại."));
        // TODO: Tạo token 1-lần, hết hạn 15 phút và gửi email reset (US-004 T1)
    }
}
