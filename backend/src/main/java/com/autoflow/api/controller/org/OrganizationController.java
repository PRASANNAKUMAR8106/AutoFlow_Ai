package com.autoflow.api.controller.org;

import com.autoflow.api.response.ApiResponse;
import com.autoflow.core.domain.Organization;
import com.autoflow.core.domain.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationRepository organizationRepository;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Organization>> getOrganization(@PathVariable UUID id) {
        return organizationRepository.findById(id)
                .map(org -> ResponseEntity.ok(ApiResponse.success(org)))
                .orElse(ResponseEntity.notFound().build());
    }
}
