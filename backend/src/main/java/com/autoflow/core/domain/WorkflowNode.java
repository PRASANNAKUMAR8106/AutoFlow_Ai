package com.autoflow.core.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "workflow_nodes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkflowNode {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_id", nullable = false)
    private Workflow workflow;

    @Column(nullable = false)
    private String type; // TRIGGER, ACTION, CONDITION

    @Column(nullable = false)
    private String nodeType; // e.g., IG_COMMENT, SEND_MESSAGE, COLLECT_EMAIL

    @Column(columnDefinition = "jsonb")
    private String configuration; // JSON containing node-specific settings

    private double posX;
    private double posY;

    private String label;
}
