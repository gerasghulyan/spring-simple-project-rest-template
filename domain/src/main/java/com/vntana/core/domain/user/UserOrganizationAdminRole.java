package com.vntana.core.domain.user;

import com.vntana.core.domain.organization.Organization;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/8/20
 * Time: 3:59 PM
 */
@Entity
@Table(name = "user_role_organization_admin", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_role_organization_admin_organization_id", columnNames = {"organization_id"}),
})
@DiscriminatorValue("ORGANIZATION_ADMIN_ROLE")
public class UserOrganizationAdminRole extends AbstractOrganizationAwareUserRole {

    UserOrganizationAdminRole() {
        super();
    }

    public UserOrganizationAdminRole(final User user, final Organization organization) {
        super(user, UserRole.ORGANIZATION_ADMIN, organization);
    }
}
