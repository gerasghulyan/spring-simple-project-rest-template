package com.vntana.core.domain.user;

import com.vntana.core.domain.client.ClientOrganization;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Vardan Aivazian
 * Date: 03.11.2020
 * Time: 14:42
 */
@Entity
@Table(name = "user_role_client_organization_viewer")
@DiscriminatorValue("CLIENT_ORGANIZATION_VIEWER_ROLE")
public class UserClientOrganizationViewerRole extends AbstractClientOrganizationAwareUserRole {

    UserClientOrganizationViewerRole() {
        super();
    }

    public UserClientOrganizationViewerRole(final User user, final ClientOrganization clientOrganization) {
        super(user, UserRole.CLIENT_ORGANIZATION_VIEWER, clientOrganization);
    }
}
