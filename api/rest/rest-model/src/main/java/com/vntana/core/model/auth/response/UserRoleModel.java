package com.vntana.core.model.auth.response;

/**
 * Created by Arthur Asatryan.
 * Date: 10/10/19
 * Time: 4:57 PM
 */
public enum UserRoleModel {
    SUPER_ADMIN,
    ORGANIZATION_OWNER,
    ORGANIZATION_ADMIN,
    ORGANIZATION_CLIENTS_VIEWER,
    CLIENT_ORGANIZATION_ADMIN {
        @Override
        public Boolean isClientRelatedRole() {
            return true;
        }
    },
    CLIENT_ORGANIZATION_CONTENT_MANAGER {
        @Override
        public Boolean isClientRelatedRole() {
            return true;
        }
    },
    CLIENT_ORGANIZATION_VIEWER {
        @Override
        public Boolean isClientRelatedRole() {
            return true;
        }
    },
    ASSET_MANAGER;

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
