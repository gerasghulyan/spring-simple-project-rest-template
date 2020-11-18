package com.vntana.core.domain.user;

/**
 * Created by Manuk Gharslyan.
 * Date: 11/18/2020
 * Time: 12:43 PM
 */
public interface UserRolePredefinedAbility {

    default Boolean hasClientAbility() {
        return false;
    }

    default Boolean hasOrganizationAbility() {
        return false;
    }

    default Boolean hasSuperAdminAbility() {
        return false;
    }
}