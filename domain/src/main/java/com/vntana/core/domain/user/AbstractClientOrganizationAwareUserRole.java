package com.vntana.core.domain.user;

import com.vntana.core.domain.client.ClientOrganization;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * Created by Manuk Gharslyan.
 * Date: 11/11/2020
 * Time: 10:16 AM
 */
@MappedSuperclass
public class AbstractClientOrganizationAwareUserRole extends AbstractUserRole {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_organization_id", nullable = false, foreignKey = @ForeignKey(name = "fk_client_organization_id"), updatable = false)
    private ClientOrganization clientOrganization;

    public AbstractClientOrganizationAwareUserRole() {
        super();
    }

    public AbstractClientOrganizationAwareUserRole(final User user, final UserRole userRole, final ClientOrganization clientOrganization) {
        super(user, userRole);
        this.clientOrganization = clientOrganization;
    }

    public ClientOrganization getClientOrganization() {
        return clientOrganization;
    }

    public void setClientOrganization(final ClientOrganization clientOrganization) {
        this.clientOrganization = clientOrganization;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof AbstractClientOrganizationAwareUserRole)) return false;

        final AbstractClientOrganizationAwareUserRole that = (AbstractClientOrganizationAwareUserRole) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(clientOrganization, that.clientOrganization)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(clientOrganization)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("clientOrganization", clientOrganization)
                .toString();
    }
}