package com.autoflow.core.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "usage_ledger")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsageLedger {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @Column(nullable = false)
    private String resourceType; // AUTOMATION_EXECUTION, AI_TOKEN, MESSAGE

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private String action; // INCREMENT, DECREMENT

    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    private String referenceId; // e.g., Execution ID
}
