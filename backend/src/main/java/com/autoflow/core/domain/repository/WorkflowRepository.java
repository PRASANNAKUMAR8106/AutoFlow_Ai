package com.autoflow.core.domain.repository;

import com.autoflow.core.domain.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface WorkflowRepository extends JpaRepository<Workflow, UUID> {
    List<Workflow> findByOrganizationId(UUID organizationId);
}
