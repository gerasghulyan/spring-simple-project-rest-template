package com.vntana.core.domain.user;

import com.vntana.core.domain.client.ClientOrganization;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Vardan Aivazian
 * Date: 03.11.2020
 * Time: 14:40
 */
@Entity
@Table(name = "user_role_client_organization_content_manager")
@DiscriminatorValue("CLIENT_ORGANIZATION_CONTENT_MANAGER_ROLE")
public class UserClientOrganizationContentManagerRole extends AbstractClientOrganizationAwareUserRole {

    UserClientOrganizationContentManagerRole() {
        super();
    }

    public UserClientOrganizationContentManagerRole(final User user, final ClientOrganization clientOrganization) {
        super(user, UserRole.CLIENT_ORGANIZATION_CONTENT_MANAGER, clientOrganization);
    }
}
