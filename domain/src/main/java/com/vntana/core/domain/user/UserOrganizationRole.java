package com.vntana.core.domain.user;

import com.vntana.core.domain.organization.Organization;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * Created by Arthur Asatryan.
 * Date: 10/10/19
 * Time: 4:53 PM
 */
@Entity
@Table(name = "user_role_organization")
@DiscriminatorValue("ORGANIZATION_ROLE")
public class UserOrganizationRole extends AbstractUserRole {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false, foreignKey = @ForeignKey(name = "fk_organization_id"), updatable = false)
    private Organization organization;

    UserOrganizationRole() {
    }

    public UserOrganizationRole(final User user, final Organization organization) {
        super(user, UserRole.ORGANIZATION_ADMIN);
        this.organization = organization;
    }

    @Override
    public UserRoleType getType() {
        return UserRoleType.ORGANIZATION_ROLE;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserOrganizationRole)) {
            return false;
        }
        final UserOrganizationRole that = (UserOrganizationRole) o;
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
                .append("organization_id", getIdOrNull(organization))
                .toString();
    }

    public Organization getOrganization() {
        return organization;
    }
}
