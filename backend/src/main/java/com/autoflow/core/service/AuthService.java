package com.autoflow.core.service;

import com.autoflow.api.dto.request.AuthRequest;
import com.autoflow.api.dto.request.RegisterRequest;
import com.autoflow.api.dto.request.CreateOrganizationRequest;
import com.autoflow.api.dto.response.AuthResponse;
import com.autoflow.core.domain.*;
import com.autoflow.core.domain.enums.UserRole;
import com.autoflow.core.domain.repository.*;
import com.autoflow.core.exception.BadRequestException;
import com.autoflow.core.exception.ResourceNotFoundException;
import com.autoflow.infrastructure.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;
    private final OrganizationRepository organizationRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @Transactional
    public User register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException("Email already registered");
        }

        User user = User.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .active(true)
                .emailVerified(false)
                .createdAt(java.time.LocalDateTime.now())
                .build();

        return userRepository.save(user);
    }

    public AuthResponse login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // For MVP, we assume the user is associated with their primary organization.
        // In a real scenario, we might prompt for organization selection.
        Membership primaryMembership = membershipRepository.findByUserId(user.getId()).stream()
                .findFirst()
                .orElseThrow(() -> new BadRequestException("User is not associated with any organization"));

        String accessToken = tokenProvider.generateToken(
                user.getEmail(),
                primaryMembership.getOrganization().getId(),
                List.of(primaryMembership.getRole().name())
        );

        String refreshToken = tokenProvider.generateRefreshToken(user.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(user.getEmail())
                .build();
    }

    @Transactional
    public Organization createOrganization(String email, CreateOrganizationRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Organization org = Organization.builder()
                .name(request.getName())
                .slug(request.getSlug())
                .description(request.getDescription())
                .active(true)
                .build();

        Organization savedOrg = organizationRepository.save(org);

        Membership membership = Membership.builder()
                .organization(savedOrg)
                .user(user)
                .role(UserRole.ADMIN)
                .build();

        membershipRepository.save(membership);

        return savedOrg;
    }

}
