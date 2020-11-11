package com.vntana.core.domain.user;

import com.vntana.core.domain.organization.Organization;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * Created by Manuk Gharslyan.
 * Date: 11/11/2020
 * Time: 10:10 AM
 */
@MappedSuperclass
public class AbstractOrganizationAwareUserRole extends AbstractUserRole {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false, foreignKey = @ForeignKey(name = "fk_organization_id"), updatable = false)
    private Organization organization;

    public AbstractOrganizationAwareUserRole() {
        super();
    }

    public AbstractOrganizationAwareUserRole(final User user, final UserRole userRole, final Organization organization) {
        super(user, userRole);
        this.organization = organization;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(final Organization organization) {
        this.organization = organization;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof AbstractOrganizationAwareUserRole)) return false;

        final AbstractOrganizationAwareUserRole that = (AbstractOrganizationAwareUserRole) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(organization, that.organization)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(organization)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("organization", organization)
                .toString();
    }
}