package com.vntana.core.domain.user;

import com.vntana.core.domain.client.ClientOrganization;
import com.vntana.core.domain.commons.AbstractDomainEntity;
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
@Table(name = "user_client_organization_role",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"client_organization_id", "user_id"},
                        name = "uk_user_id_and_client_organization_id"
                )
        }
)
public class UserClientOrganizationRole extends AbstractDomainEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_user_id"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_organization_id", nullable = false, foreignKey = @ForeignKey(name = "fk_client_organization_id"))
    private ClientOrganization clientOrganization;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole userRole;

    public UserClientOrganizationRole() {
    }

    public UserClientOrganizationRole(final User user, final ClientOrganization clientOrganization, final UserRole userRole) {
        this.user = user;
        this.clientOrganization = clientOrganization;
        this.userRole = userRole;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserClientOrganizationRole)) {
            return false;
        }
        final UserClientOrganizationRole that = (UserClientOrganizationRole) o;
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
                .append("client_organization_id", getIdOrNull(clientOrganization))
                .append("userRole", userRole)
                .toString();
    }

    public User getUser() {
        return user;
    }

    public ClientOrganization getClientOrganization() {
        return clientOrganization;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(final UserRole userRole) {
        this.userRole = userRole;
    }
}
