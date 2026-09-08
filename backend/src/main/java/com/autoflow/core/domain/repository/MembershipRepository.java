package com.autoflow.core.domain.repository;

import com.autoflow.core.domain.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, UUID> {
    Optional<Membership> findByOrganizationIdAndUserId(UUID organizationId, UUID userId);
    List<Membership> findByOrganizationId(UUID organizationId);
    List<Membership> findByUserId(UUID userId);
}
