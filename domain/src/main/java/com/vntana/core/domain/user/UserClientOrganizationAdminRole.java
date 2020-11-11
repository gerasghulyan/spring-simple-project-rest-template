package com.vntana.core.domain.user;

import com.vntana.core.domain.client.ClientOrganization;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Arthur Asatryan.
 * Date: 10/10/19
 * Time: 4:53 PM
 */
@Entity
@Table(name = "user_role_client_organization_admin")
@DiscriminatorValue("CLIENT_ORGANIZATION_ADMIN_ROLE")
public class UserClientOrganizationAdminRole extends AbstractClientOrganizationAwareUserRole {

    UserClientOrganizationAdminRole() {
        super();
    }

    public UserClientOrganizationAdminRole(final User user, final ClientOrganization clientOrganization) {
        super(user, UserRole.CLIENT_ORGANIZATION_ADMIN, clientOrganization);
    }
}
