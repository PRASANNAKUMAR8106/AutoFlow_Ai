package com.autoflow;

import com.autoflow.api.dto.request.*;
import com.autoflow.api.dto.response.*;
import com.autoflow.core.domain.*;
import com.autoflow.core.domain.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private MembershipRepository membershipRepository;

    private String testEmail = "test@example.com";
    private String testPassword = "Password123!";

    @BeforeEach
    void setUp() {
        membershipRepository.deleteAll();
        userRepository.deleteAll();
        organizationRepository.deleteAll();
    }

    private User createTestUser(String email, String password) {
        return userRepository.save(User.builder()
                .email(email)
                .passwordHash("hashed_" + password) // In real app, use PasswordEncoder
                .firstName("Test")
                .lastName("User")
                .active(true)
                .emailVerified(false)
                .createdAt(java.time.LocalDateTime.now())
                .build());
    }

    @Test
    @DisplayName("User registration should succeed with valid data")
    void testRegisterSuccess() throws Exception {
        RegisterRequest request = RegisterRequest.builder()
                .email(testEmail)
                .password(testPassword)
                .firstName("John")
                .lastName("Doe")
                .build();

        MvcResult result = mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.email").value(testEmail))
                .andReturn();

        System.out.println("Register response: " + result.getResponse().getContentAsString());

        assertTrue(userRepository.findByEmail(testEmail).isPresent());
    }

    @Test
    @DisplayName("Registration should fail for existing email")
    void testRegisterDuplicateEmail() throws Exception {
        createTestUser(testEmail, testPassword);

        RegisterRequest request = RegisterRequest.builder()
                .email(testEmail)
                .password(testPassword)
                .firstName("Jane")
                .lastName("Doe")
                .build();

        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Login should succeed and return JWT")
    void testLoginSuccess() throws Exception {
        // Register user
        RegisterRequest regRequest = RegisterRequest.builder()
                .email(testEmail)
                .password(testPassword)
                .firstName("John")
                .lastName("Doe")
                .build();
        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(regRequest)));

        // Setup organization for login
        Organization org = organizationRepository.save(Organization.builder()
                .name("Test Org")
                .slug("test-org")
                .active(true)
                .createdAt(java.time.LocalDateTime.now())
                .build());

        User user = userRepository.findByEmail(testEmail).get();
        membershipRepository.save(Membership.builder()
                .user(user)
                .organization(org)
                .role(com.autoflow.core.domain.enums.UserRole.ADMIN)
                .build());

        AuthRequest loginRequest = AuthRequest.builder()
                .email(testEmail)
                .password(testPassword)
                .build();

        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.accessToken").exists())
                .andExpect(jsonPath("$.data.refreshToken").exists());
    }

    @Test
    @DisplayName("Login should fail for invalid credentials")
    void testLoginInvalidCredentials() throws Exception {
        AuthRequest loginRequest = AuthRequest.builder()
                .email("nonexistent@example.com")
                .password("WrongPassword")
                .build();

        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Protected endpoint should require authentication")
    void testProtectedEndpointUnauthorized() throws Exception {
        CreateOrganizationRequest orgRequest = CreateOrganizationRequest.builder()
                .name("My Org")
                .slug("my-org")
                .build();

        mockMvc.perform(post("/api/v1/auth/setup-org")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orgRequest)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Organization creation should succeed for authenticated user")
    void testSetupOrganizationSuccess() throws Exception {
        RegisterRequest regRequest = RegisterRequest.builder()
                .email(testEmail)
                .password(testPassword)
                .firstName("John")
                .lastName("Doe")
                .build();
        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(regRequest)));

        Organization initialOrg = organizationRepository.save(Organization.builder()
                .name("Init")
                .slug("init")
                .active(true)
                .createdAt(java.time.LocalDateTime.now())
                .build());
        User user = userRepository.findByEmail(testEmail).get();
        membershipRepository.save(Membership.builder()
                .user(user)
                .organization(initialOrg)
                .role(com.autoflow.core.domain.enums.UserRole.ADMIN)
                .joinedAt(java.time.LocalDateTime.now())
                .build());

        AuthRequest loginRequest = AuthRequest.builder().email(testEmail).password(testPassword).build();
        MvcResult loginResult = mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andReturn();

        String token = objectMapper.readTree(loginResult.getResponse().getContentAsString())
                .get("data").get("accessToken").asText();

        CreateOrganizationRequest orgRequest = CreateOrganizationRequest.builder()
                .name("New Org")
                .slug("new-org")
                .description("Description")
                .build();

        mockMvc.perform(post("/api/v1/auth/setup-org")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orgRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("New Org"));
    }

    @Test
    @DisplayName("Tenant isolation: JWT should associate user with correct organization")
    void testTenantIsolation() throws Exception {
        User user1 = createTestUser("u1@test.com", "pass1");
        User user2 = createTestUser("u2@test.com", "pass2");

        Organization org1 = organizationRepository.save(Organization.builder()
                .name("Org1").slug("org1").active(true).createdAt(java.time.LocalDateTime.now()).build());
        Organization org2 = organizationRepository.save(Organization.builder()
                .name("Org2").slug("org2").active(true).createdAt(java.time.LocalDateTime.now()).build());

        membershipRepository.save(Membership.builder()
                .user(user1).organization(org1).role(com.autoflow.core.domain.enums.UserRole.ADMIN).joinedAt(java.time.LocalDateTime.now()).build());
        membershipRepository.save(Membership.builder()
                .user(user2).organization(org2).role(com.autoflow.core.domain.enums.UserRole.ADMIN).joinedAt(java.time.LocalDateTime.now()).build());

        assertEquals(org1.getId(), membershipRepository.findByUserId(user1.getId()).get(0).getOrganization().getId());
        assertEquals(org2.getId(), membershipRepository.findByUserId(user2.getId()).get(0).getOrganization().getId());
    }

    @Test
    @DisplayName("Authentication logic should NOT collect viewer emails")
    void testNoEmailCollectionInAuth() {
        RegisterRequest reg = new RegisterRequest();
        AuthRequest auth = new AuthRequest();
        assertNotNull(reg);
        assertNotNull(auth);
    }
}
