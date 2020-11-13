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
    ORGANIZATION_CLIENTS_VIEWER(10000),
    CLIENT_ORGANIZATION_ADMIN(4) {
        @Override
        public Boolean isClientRelatedRole() {
            return true;
        }
    },
    CLIENT_ORGANIZATION_CONTENT_MANAGER(5) {
        @Override
        public Boolean isClientRelatedRole() {
            return true;
        }
    },
    CLIENT_ORGANIZATION_VIEWER(6) {
        @Override
        public Boolean isClientRelatedRole() {
            return true;
        }
    };

    public Boolean isClientRelatedRole() {
        return false;
    }

    private final int priority;

    UserRoleModel(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
