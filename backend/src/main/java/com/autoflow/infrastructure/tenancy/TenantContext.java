package com.autoflow.infrastructure.tenancy;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

/**
 * Holder for the current tenant (organization) context.
 * This is used throughout the request lifecycle to ensure data isolation.
 */
public class TenantContext {
    private static final ThreadLocal<UUID> CURRENT_TENANT = new ThreadLocal<>();

    public static UUID getCurrentTenant() {
        return CURRENT_TENANT.get();
    }

    public static void setCurrentTenant(UUID tenantId) {
        CURRENT_TENANT.set(tenantId);
    }

    public static void clear() {
        CURRENT_TENANT.remove();
    }
}
