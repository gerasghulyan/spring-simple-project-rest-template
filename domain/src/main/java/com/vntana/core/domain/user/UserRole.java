package com.vntana.core.domain.user;

/**
 * Created by Arthur Asatryan.
 * Date: 10/10/19
 * Time: 4:57 PM
 */
public enum UserRole implements UserRolePredefinedAbility {
    SUPER_ADMIN {
        @Override
        public Boolean hasSuperAdminAbility() {
            return true;
        }
    },
    ORGANIZATION_OWNER {
        @Override
        public Boolean hasOrganizationAbility() {
            return true;
        }
    },
    ORGANIZATION_ADMIN {
        @Override
        public Boolean hasOrganizationAbility() {
            return true;
        }
    },
    CLIENT_ORGANIZATION_ADMIN {
        @Override
        public Boolean hasClientAbility() {
            return true;
        }
    },
    CLIENT_ORGANIZATION_CONTENT_MANAGER {
        @Override
        public Boolean hasClientAbility() {
            return true;
        }
    },
    CLIENT_ORGANIZATION_VIEWER {
        @Override
        public Boolean hasClientAbility() {
            return true;
        }
    };
}
