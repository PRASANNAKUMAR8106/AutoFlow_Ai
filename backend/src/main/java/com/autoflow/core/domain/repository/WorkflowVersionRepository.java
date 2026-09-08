package com.autoflow.core.domain.repository;

import com.autoflow.core.domain.WorkflowVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface WorkflowVersionRepository extends JpaRepository<WorkflowVersion, UUID> {
    List<WorkflowVersion> findByWorkflowIdOrderByVersionNumberDesc(UUID workflowId);
}
