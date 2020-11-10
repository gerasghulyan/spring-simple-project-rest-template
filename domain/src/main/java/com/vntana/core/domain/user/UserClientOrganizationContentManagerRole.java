package com.vntana.core.domain.user;

import com.vntana.core.domain.client.ClientOrganization;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * Created by Vardan Aivazian
 * Date: 03.11.2020
 * Time: 14:40
 */
@Entity
@Table(name = "user_role_client_organization_content_manager")
@DiscriminatorValue("CLIENT_ORGANIZATION_CONTENT_MANAGER_ROLE")
public class UserClientOrganizationContentManagerRole extends AbstractUserRole {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_organization_id", nullable = false, foreignKey = @ForeignKey(name = "fk_client_organization_id"), updatable = false)
    private ClientOrganization clientOrganization;

    UserClientOrganizationContentManagerRole() {
        super();
    }

    public UserClientOrganizationContentManagerRole(final User user, final ClientOrganization clientOrganization) {
        super(user, UserRole.CLIENT_ORGANIZATION_CONTENT_MANAGER);
        this.clientOrganization = clientOrganization;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserClientOrganizationContentManagerRole)) {
            return false;
        }
        final UserClientOrganizationContentManagerRole that = (UserClientOrganizationContentManagerRole) o;
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

    public ClientOrganization getClientOrganization() {
        return clientOrganization;
    }
}
