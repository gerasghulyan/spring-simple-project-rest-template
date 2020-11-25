package com.vntana.core.model.auth.response;

/**
 * Created by Arthur Asatryan.
 * Date: 10/10/19
 * Time: 4:57 PM
 */
public enum UserRoleModel implements UserRoleModelPredefinedAbility {
    SUPER_ADMIN(1) {
        @Override
        public Boolean hasSuperAdminAbility() {
            return true;
        }
    },
    ORGANIZATION_OWNER(2) {
        @Override
        public Boolean hasOrganizationAbility() {
            return true;
        }
    },
    ORGANIZATION_ADMIN(3) {
        @Override
        public Boolean hasOrganizationAbility() {
            return true;
        }
    },
    ORGANIZATION_CLIENT_MEMBER {
        @Override
        public Boolean hasNeutralAbility() {
            return true;
        }

        @Override
        public Boolean hasInviterAbility() {
            return false;
        }

        @Override
        public Boolean hasSubscriptionAbility() {
            return false;
        }
    },
    CLIENT_ORGANIZATION_ADMIN(4) {
        @Override
        public Boolean hasClientAbility() {
            return true;
        }

        @Override
        public Boolean hasSubscriptionAbility() {
            return false;
        }
    },
    CLIENT_ORGANIZATION_CONTENT_MANAGER(5) {
        @Override
        public Boolean hasClientAbility() {
            return true;
        }

        @Override
        public Boolean hasSubscriptionAbility() {
            return false;
        }
    },
    CLIENT_ORGANIZATION_VIEWER(6) {
        @Override
        public Boolean hasClientAbility() {
            return true;
        }

        @Override
        public Boolean hasInviterAbility() {
            return false;
        }

        @Override
        public Boolean hasSubscriptionAbility() {
            return false;
        }
    };

    private int priority;

    UserRoleModel() {
    }

    UserRoleModel(final int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
