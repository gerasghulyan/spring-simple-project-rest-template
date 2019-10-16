package com.vntana.core.domain.user;

import com.vntana.core.domain.client.ClientOrganization;
import com.vntana.core.domain.commons.AbstractUuidAwareDomainEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

/**
 * Created by Arthur Asatryan.
 * Date: 10/3/19
 * Time: 5:37 PM
 */
@Entity
@Table(name = "user_")
public class User extends AbstractUuidAwareDomainEntity {

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserClientOrganizationRole> clientRoles;

    public User() {
    }

    public User(final String fullName, final String email, final String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    public void grantClientRole(final ClientOrganization clientOrganization, final UserRole userRole) {
        if (roleOfClient(clientOrganization).isPresent()) {
            throw new IllegalStateException(format("User - %s already has role in client organization - %s", this, clientOrganization));
        }
        final UserClientOrganizationRole role = new UserClientOrganizationRole(this, clientOrganization, userRole);
        mutableClientRoles().add(role);
    }

    public void revokeClientRole(final ClientOrganization clientOrganization) {
        final UserClientOrganizationRole role = roleOfClient(clientOrganization)
                .orElseThrow(() -> new IllegalStateException(format("User - %s does not have role in client organization - %s", this, clientOrganization)));
        mutableClientRoles().remove(immutableClientRoles().indexOf(role));
    }

    public Optional<UserClientOrganizationRole> roleOfClient(final ClientOrganization clientOrganization) {
        return immutableClientRoles().stream()
                .filter(role -> role.getClientOrganization().equals(clientOrganization))
                .findAny();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    private List<UserClientOrganizationRole> mutableClientRoles() {
        if (clientRoles == null) {
            clientRoles = new ArrayList<>();
            return clientRoles;
        } else {
            return clientRoles;
        }
    }

    private List<UserClientOrganizationRole> immutableClientRoles() {
        return Optional.ofNullable(clientRoles)
                .map(Collections::unmodifiableList)
                .orElseGet(Collections::emptyList);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        final User user = (User) o;
        return new EqualsBuilder()
                .append(getUuid(), user.getUuid())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getUuid())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("fullName", fullName)
                .append("email", email)
                .toString();
    }
}
