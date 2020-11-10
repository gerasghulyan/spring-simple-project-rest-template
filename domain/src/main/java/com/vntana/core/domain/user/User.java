package com.vntana.core.domain.user;

import com.vntana.commons.persistence.domain.AbstractUuidAwareDomainEntity;
import com.vntana.core.domain.client.ClientOrganization;
import com.vntana.core.domain.organization.Organization;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * Created by Arthur Asatryan.
 * Date: 10/3/19
 * Time: 5:37 PM
 */
@Entity
@Table(name = "user_", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_uuid", columnNames = {"uuid"}),
        @UniqueConstraint(name = "uk_user_email", columnNames = {"email"})
})
public class User extends AbstractUuidAwareDomainEntity {

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "verified", nullable = false)
    private Boolean verified;

    @Column(name = "image_blob_id")
    private String imageBlobId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AbstractUserRole> roles;

    User() {
    }

    public User(final String fullName, final String email, final String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.verified = false;
    }

    //region Public methods
    public void grantClientRole(final ClientOrganization clientOrganization, final UserRole userRole) {
        if (roleOfClient(clientOrganization).isPresent()) {
            throw new IllegalStateException(format("User - %s already has role in client organization - %s", this, clientOrganization));
        }
        final AbstractUserRole role = buildClientRole(userRole, clientOrganization);
        mutableRoles().add(role);
    }

    public void grantOrganizationOwnerRole(final Organization organization) {
        if (roleOfOrganizationOwner(organization).isPresent()) {
            throw new IllegalStateException(format("User - %s already has role in organization - %s", this, organization));
        }
        final AbstractUserRole role = new UserOrganizationOwnerRole(this, organization);
        mutableRoles().add(role);
    }

    public void grantSuperAdminRole() {
        roleOfSuperAdmin().ifPresent(role -> {
            throw new IllegalStateException(format("User - %s already has super admin role - %s", this, role));
        });
        final AbstractUserRole role = new UserSuperAdminRole(this);
        mutableRoles().add(role);
    }

    public void revokeClientRole(final ClientOrganization clientOrganization) {
        final UserClientOrganizationAdminRole role = roleOfClient(clientOrganization)
                .orElseThrow(() -> new IllegalStateException(format("User - %s does not have role in client organization - %s", this, clientOrganization)));
        mutableRoles().remove(role);
    }

    public Optional<UserClientOrganizationAdminRole> roleOfClient(final ClientOrganization clientOrganization) {
        return immutableClientRoles().stream()
                .filter(role -> role.getClientOrganization().equals(clientOrganization))
                .findAny();
    }

    public Optional<UserOrganizationOwnerRole> roleOfOrganizationOwner(final Organization organization) {
        return immutableOrganizationOwnerRoles().stream()
                .filter(role -> role.getOrganization().equals(organization))
                .findAny();
    }

    public Optional<UserSuperAdminRole> roleOfSuperAdmin() {
        return immutableRoles().stream()
                .filter(role -> role.getUserRole().equals(UserRole.SUPER_ADMIN))
                .findAny()
                .map(UserSuperAdminRole.class::cast);
    }

    public String getFullName() {
        return fullName;
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

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(final Boolean verified) {
        this.verified = verified;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    public String getImageBlobId() {
        return imageBlobId;
    }

    public void setImageBlobId(final String imageBlobId) {
        this.imageBlobId = imageBlobId;
    }

    public List<AbstractUserRole> roles() {
        return immutableRoles();
    }

    public void setRoles(final List<AbstractUserRole> roles) {
        this.roles = roles;
    }
    //endregion

    //region Utility methods
    public List<UserOrganizationOwnerRole> immutableOrganizationOwnerRoles() {
        return Optional.ofNullable(roles)
                .map(theRoles -> theRoles.stream()
                        .filter(UserOrganizationOwnerRole.class::isInstance)
                        .map(UserOrganizationOwnerRole.class::cast)
                        .collect(Collectors.toList())
                )
                .map(Collections::unmodifiableList)
                .orElseGet(Collections::emptyList);
    }

    public AbstractUserRole buildClientRole(final UserRole userRole, final ClientOrganization clientOrganization) {
        switch (userRole) {
            case CLIENT_ORGANIZATION_ADMIN:
                return new UserClientOrganizationAdminRole(this, clientOrganization);
            case CLIENT_ORGANIZATION_CONTENT_MANAGER:
                return new UserClientOrganizationContentManagerRole(this, clientOrganization);
            case CLIENT_ORGANIZATION_VIEWER:
                return new UserClientOrganizationViewerRole(this, clientOrganization);
            default:
                throw new IllegalStateException(format("Unknown user client role %s", userRole));
        }
    }

    private List<AbstractUserRole> mutableRoles() {
        if (roles == null) {
            roles = new ArrayList<>();
            return roles;
        } else {
            return roles;
        }
    }

    private List<UserClientOrganizationAdminRole> immutableClientRoles() {
        return Optional.ofNullable(roles)
                .map(theRoles -> theRoles.stream()
                        .filter(UserClientOrganizationAdminRole.class::isInstance)
                        .map(UserClientOrganizationAdminRole.class::cast)
                        .collect(Collectors.toList())
                )
                .map(Collections::unmodifiableList)
                .orElseGet(Collections::emptyList);
    }

    private List<AbstractUserRole> immutableRoles() {
        return Optional.ofNullable(roles)
                .map(Collections::unmodifiableList)
                .orElseGet(Collections::emptyList);
    }
    //endregion

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        final User user = (User) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(this.fullName, user.fullName)
                .append(this.email, user.email)
                .append(this.verified, user.verified)
                .append(this.imageBlobId, user.imageBlobId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(this.fullName)
                .append(this.email)
                .append(this.verified)
                .append(this.imageBlobId)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("fullName", fullName)
                .append("email", email)
                .append("imageBlobId", imageBlobId)
                .append("verified", verified)
                .toString();
    }
}
