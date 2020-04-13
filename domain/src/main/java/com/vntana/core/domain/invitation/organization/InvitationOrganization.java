package com.vntana.core.domain.invitation.organization;

import com.vntana.commons.persistence.domain.AbstractUuidAwareDomainEntity;
import com.vntana.core.domain.invitation.InvitationStatus;
import com.vntana.core.domain.organization.Organization;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/25/20
 * Time: 2:44 PM
 */
@Entity
@Table(name = "invitation_organization")
public class InvitationOrganization extends AbstractUuidAwareDomainEntity {

    @Column(name = "owner_full_name", nullable = false)
    private String ownerFullName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "organization_name", nullable = false)
    private String organizationName;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "customer_subscription_definition_uuid", nullable = false)
    private String customerSubscriptionDefinitionUuid;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false)
    private InvitationStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", foreignKey = @ForeignKey(name = "fk_invitation_organization_organization_id"), updatable = false)
    private Organization organization;

    InvitationOrganization() {
        super();
    }

    public InvitationOrganization(final String ownerFullName,
                                  final String email,
                                  final String organizationName,
                                  final String slug,
                                  final String customerSubscriptionDefinitionUuid,
                                  final InvitationStatus status) {
        super();
        this.ownerFullName = ownerFullName;
        this.email = email;
        this.organizationName = organizationName;
        this.slug = slug;
        this.customerSubscriptionDefinitionUuid = customerSubscriptionDefinitionUuid;
        this.status = status;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InvitationOrganization)) {
            return false;
        }
        final InvitationOrganization that = (InvitationOrganization) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(ownerFullName, that.ownerFullName)
                .append(email, that.email)
                .append(organizationName, that.organizationName)
                .append(slug, that.slug)
                .append(customerSubscriptionDefinitionUuid, that.customerSubscriptionDefinitionUuid)
                .append(status, that.status)
                .append(organization, that.organization)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(ownerFullName)
                .append(email)
                .append(organizationName)
                .append(slug)
                .append(customerSubscriptionDefinitionUuid)
                .append(status)
                .append(organization)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("ownerFullName", ownerFullName)
                .append("email", email)
                .append("organizationName", organizationName)
                .append("slug", slug)
                .append("customerSubscriptionDefinitionUuid", customerSubscriptionDefinitionUuid)
                .append("status", status)
                .append("organization", organization)
                .toString();
    }

    public String getOwnerFullName() {
        return ownerFullName;
    }

    public String getEmail() {
        return email;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getSlug() {
        return slug;
    }

    public String getCustomerSubscriptionDefinitionUuid() {
        return customerSubscriptionDefinitionUuid;
    }

    public InvitationStatus getStatus() {
        return status;
    }

    public void setStatus(final InvitationStatus status) {
        this.status = status;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(final Organization organization) {
        this.organization = organization;
    }
}
