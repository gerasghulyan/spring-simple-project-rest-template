package com.vntana.core.model.auth.response;

/**
 * Created by Arthur Asatryan.
 * Date: 10/10/19
 * Time: 4:57 PM
 */
public enum UserRoleModel {
    SUPER_ADMIN(1),
    ORGANIZATION_OWNER(2),
    ORGANIZATION_ADMIN(3),
    CLIENT_ORGANIZATION_ADMIN(4),
    CLIENT_ORGANIZATION_CONTENT_MANAGER(5),
    ASSET_MANAGER(6),
    CLIENT_ORGANIZATION_VIEWER(7);

    private final int priority;

    UserRoleModel(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
