package com.vntana.core.domain.invitation.user;

import com.vntana.commons.persistence.domain.AbstractUuidAwareDomainEntity;
import com.vntana.core.domain.invitation.InvitationStatus;
import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.user.User;
import com.vntana.core.domain.user.UserRole;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 11:44 AM
 */
@Entity
@Table(name = "invitation_organization_user", uniqueConstraints = {
        @UniqueConstraint(name = "uk_invitation_organization_user_uuid", columnNames = {"uuid"})
})
public class InvitationOrganizationUser extends AbstractUuidAwareDomainEntity {

    @Enumerated(value = EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole role;

    @Column(name = "email", nullable = false)
    private String email;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false)
    private InvitationStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_invitation_organization_user_user_id"), updatable = false)
    private User inviterUser;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false, foreignKey = @ForeignKey(name = "fk_invitation_organization_user_organization_id"), updatable = false)
    private Organization organization;

    public InvitationOrganizationUser() {
        super();
    }

    public InvitationOrganizationUser(final UserRole role,
                                      final String email,
                                      final InvitationStatus status,
                                      final User inviterUser, final Organization organization) {
        super();
        this.role = role;
        this.email = email;
        this.status = status;
        this.inviterUser = inviterUser;
        this.organization = organization;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof InvitationOrganizationUser)) return false;

        final InvitationOrganizationUser that = (InvitationOrganizationUser) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(role, that.role)
                .append(email, that.email)
                .append(status, that.status)
                .append(getIdOrNull(inviterUser), getIdOrNull(that.inviterUser))
                .append(getIdOrNull(organization), getIdOrNull(that.organization))
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(role)
                .append(email)
                .append(status)
                .append(getIdOrNull(inviterUser))
                .append(getIdOrNull(organization))
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("role", role)
                .append("email", email)
                .append("status", status)
                .append("inviterUser", getIdOrNull(inviterUser))
                .append("organization", getIdOrNull(organization))
                .toString();
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(final UserRole role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public InvitationStatus getStatus() {
        return status;
    }

    public void setStatus(final InvitationStatus status) {
        this.status = status;
    }

    public User getInviterUser() {
        return inviterUser;
    }

    public void setInviterUser(final User inviterUser) {
        this.inviterUser = inviterUser;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(final Organization organization) {
        this.organization = organization;
    }
}