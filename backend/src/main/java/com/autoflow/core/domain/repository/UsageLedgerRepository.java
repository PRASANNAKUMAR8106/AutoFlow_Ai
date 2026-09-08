package com.autoflow.core.domain.repository;

import com.autoflow.core.domain.UsageLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface UsageLedgerRepository extends JpaRepository<UsageLedger, UUID> {
    List<UsageLedger> findByOrganizationId(UUID organizationId);
}
