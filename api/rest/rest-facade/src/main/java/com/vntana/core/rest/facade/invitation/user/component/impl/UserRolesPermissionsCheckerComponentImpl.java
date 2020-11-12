package com.vntana.core.rest.facade.invitation.user.component.impl;

import com.vntana.core.model.auth.response.UserRoleModel;
import com.vntana.core.rest.facade.invitation.user.component.UserRolesPermissionsCheckerComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Diana Gevorgyan
 * Date: 11/12/20
 * Time: 3:05 PM
 */
@Component
public class UserRolesPermissionsCheckerComponentImpl implements UserRolesPermissionsCheckerComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRolesPermissionsCheckerComponentImpl.class);
    
    public UserRolesPermissionsCheckerComponentImpl() {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Override
    public boolean isPermittedToInvite(final UserRoleModel inviter, final UserRoleModel invited) {
        if (inviter == UserRoleModel.ORGANIZATION_OWNER) {
            return invited != UserRoleModel.ORGANIZATION_OWNER;
        }
        if (inviter == UserRoleModel.CLIENT_ORGANIZATION_VIEWER) {
            return false;
        }
        return inviter.getPriority() <= invited.getPriority();
    }
}
