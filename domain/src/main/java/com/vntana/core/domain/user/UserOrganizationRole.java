package com.vntana.core.domain.user;

import com.vntana.core.domain.commons.AbstractDomainEntity;
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
@Table(name = "user_organization_role",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"organization_id", "user_id"},
                        name = "uk_user_id_and_organization_id"
                )
        }
)
public class UserOrganizationRole extends AbstractDomainEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_user_id"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false, foreignKey = @ForeignKey(name = "fk_organization_id"))
    private Organization organization;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole userRole;

    public UserOrganizationRole() {
    }

    public UserOrganizationRole(final User user, final Organization organization, final UserRole userRole) {
        this.user = user;
        this.organization = organization;
        this.userRole = userRole;
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
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("user_id", getIdOrNull(user))
                .append("organization_id", getIdOrNull(organization))
                .append("userRole", userRole)
                .toString();
    }

    public User getUser() {
        return user;
    }

    public Organization getOrganization() {
        return organization;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(final UserRole userRole) {
        this.userRole = userRole;
    }
}
