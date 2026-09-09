package com.autoflow.api.controller.auth;

import com.autoflow.api.dto.request.AuthRequest;
import com.autoflow.api.dto.request.CreateOrganizationRequest;
import com.autoflow.api.dto.request.RegisterRequest;
import com.autoflow.api.response.ApiResponse;
import com.autoflow.api.dto.response.AuthResponse;
import com.autoflow.core.domain.Organization;
import com.autoflow.core.domain.User;
import com.autoflow.core.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> register(@Valid @RequestBody RegisterRequest request) {
        User user = authService.register(request);
        return ResponseEntity.ok(ApiResponse.success("User registered successfully", user));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody AuthRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Login successful", response));
    }

    @PostMapping("/setup-org")
    public ResponseEntity<ApiResponse<Organization>> setupOrganization(@Valid @RequestBody CreateOrganizationRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Organization org = authService.createOrganization(email, request);
        return ResponseEntity.ok(ApiResponse.success("Organization created successfully", org));
    }
}
