package com.autoflow.core.domain.repository;

import com.autoflow.core.domain.WorkflowEdge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface WorkflowEdgeRepository extends JpaRepository<WorkflowEdge, UUID> {
    List<WorkflowEdge> findByWorkflowId(UUID workflowId);
}
