package com.vntana.core.domain.user;

import com.vntana.core.domain.organization.Organization;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Created by Arthur Asatryan.
 * Date: 10/10/19
 * Time: 4:53 PM
 */
@Entity
@Table(name = "user_role_organization_owner", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_role_organization_owner_organization_id", columnNames = {"organization_id"}),
})
@DiscriminatorValue("ORGANIZATION_OWNER_ROLE")
public class UserOrganizationOwnerRole extends AbstractOrganizationAwareUserRole {

    UserOrganizationOwnerRole() {
        super();
    }

    public UserOrganizationOwnerRole(final User user, final Organization organization) {
        super(user, UserRole.ORGANIZATION_OWNER, organization);
    }
}
