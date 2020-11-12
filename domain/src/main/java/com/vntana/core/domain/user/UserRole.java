package com.vntana.core.domain.user;

/**
 * Created by Arthur Asatryan.
 * Date: 10/10/19
 * Time: 4:57 PM
 */
public enum UserRole {
    SUPER_ADMIN,
    ORGANIZATION_OWNER,
    ORGANIZATION_ADMIN,
    CLIENT_ORGANIZATION_ADMIN,
    CLIENT_ORGANIZATION_CONTENT_MANAGER,
    CLIENT_ORGANIZATION_VIEWER;
    
    public boolean isClientRelatedRole() {
        return this.equals(CLIENT_ORGANIZATION_ADMIN) || this.equals(CLIENT_ORGANIZATION_CONTENT_MANAGER) || this.equals(CLIENT_ORGANIZATION_VIEWER);
    }
}
