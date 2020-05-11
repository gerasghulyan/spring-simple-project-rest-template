package com.vntana.core.domain.user;

import com.vntana.core.domain.organization.Organization;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

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
public class UserOrganizationAdminRole extends AbstractUserRole {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false, foreignKey = @ForeignKey(name = "fk_organization_id"), updatable = false)
    private Organization organization;

    UserOrganizationAdminRole() {
        super();
    }

    public UserOrganizationAdminRole(final User user, final Organization organization) {
        super(user, UserRole.ORGANIZATION_ADMIN);
        this.organization = organization;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserOrganizationAdminRole)) {
            return false;
        }
        final UserOrganizationAdminRole that = (UserOrganizationAdminRole) o;
        return new EqualsBuilder()
                .append(getId(), that.getId())
                .append(getIdOrNull(organization), that.getIdOrNull(organization))
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getId())
                .append(getIdOrNull(organization))
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("organizationId", getIdOrNull(organization))
                .toString();
    }

    public Organization getOrganization() {
        return organization;
    }
}
