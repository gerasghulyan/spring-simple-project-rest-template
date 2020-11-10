package com.vntana.core.rest.facade.invitation.user.checker.impl;

import com.vntana.core.model.auth.response.UserRoleModel;
import com.vntana.core.rest.facade.invitation.user.checker.UserRolesComparator;
import org.springframework.stereotype.Component;

/**
 * Created by Diana Gevorgyan
 * Date: 11/10/20
 * Time: 6:12 PM
 */
@Component
public class UserRoleComparatorImpl implements UserRolesComparator {

    @Override
    public boolean compare(final UserRoleModel comparator, final UserRoleModel comparable) {
        if (comparator == UserRoleModel.ORGANIZATION_OWNER && comparable == UserRoleModel.ORGANIZATION_OWNER) {
            return false;
        }
        if (comparator == UserRoleModel.CLIENT_CONTENT_VIEWER) {
            return false;
        }
        return comparator.getPriority() >= comparable.getPriority();
    }
}
