package com.vntana.core.model.auth.response;

/**
 * Created by Manuk Gharslyan.
 * Date: 11/17/2020
 * Time: 6:17 PM
 */
public interface UserRoleModelPredefinedAbility {

    default Boolean hasNeutralAbility() {
        return false;
    }

    default Boolean hasClientAbility() {
        return false;
    }

    default Boolean hasOrganizationAbility() {
        return false;
    }

    default Boolean hasSuperAdminAbility() {
        return false;
    }

    default Boolean hasInviterAbility() {
        return true;
    }

    default Boolean hasSubscriptionAbility() {
        return true;
    }
}