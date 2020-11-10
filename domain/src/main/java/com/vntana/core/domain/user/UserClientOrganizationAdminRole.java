package com.vntana.core.domain.user;

import com.vntana.core.domain.client.ClientOrganization;
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
@Table(name = "user_role_client_organization_admin")
@DiscriminatorValue("CLIENT_ADMIN_ROLE")
public class UserClientOrganizationAdminRole extends AbstractUserRole {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_organization_id", nullable = false, foreignKey = @ForeignKey(name = "fk_client_organization_id"), updatable = false)
    private ClientOrganization clientOrganization;

    UserClientOrganizationAdminRole() {
        super();
    }

    public UserClientOrganizationAdminRole(final User user, final ClientOrganization clientOrganization) {
        super(user, UserRole.CLIENT_ADMIN);
        this.clientOrganization = clientOrganization;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserClientOrganizationAdminRole)) {
            return false;
        }
        final UserClientOrganizationAdminRole that = (UserClientOrganizationAdminRole) o;
        return new EqualsBuilder()
                .append(getId(), that.getId())
                .append(getIdOrNull(clientOrganization), that.getIdOrNull(clientOrganization))
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getId())
                .append(getIdOrNull(clientOrganization))
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("client_organization_id", getIdOrNull(clientOrganization))
                .toString();
    }

    public ClientOrganization getClientOrganization() {
        return clientOrganization;
    }
}
