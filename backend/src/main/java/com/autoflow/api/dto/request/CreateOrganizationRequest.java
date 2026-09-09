package com.autoflow.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrganizationRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String slug;

    private String description;
}
