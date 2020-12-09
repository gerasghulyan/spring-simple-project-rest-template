package com.vntana.core.domain.organization;

import com.vntana.commons.persistence.domain.AbstractUuidAwareDomainEntity;
import com.vntana.core.domain.client.ClientOrganization;
import com.vntana.core.domain.invitation.organization.InvitationOrganization;
import com.vntana.core.domain.organization.status.OrganizationStatus;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.*;


/**
 * Created by Arthur Asatryan.
 * Date: 10/15/19
 * Time: 3:40 PM
 */
@Entity
@Table(name = "organization", uniqueConstraints = {
        @UniqueConstraint(name = "uk_organization_id_slug", columnNames = {"organization_slug"}),
        @UniqueConstraint(name = "uk_organization_uuid", columnNames = {"uuid"}),
        @UniqueConstraint(name = "uk_organization_invitation_organization_id", columnNames = {"invitation_organization_id"})
})
public class Organization extends AbstractUuidAwareDomainEntity {

    @Column(name = "organization_name", nullable = false)
    private String name;

    @Column(name = "organization_slug", nullable = false, updatable = false, unique = true)
    private String slug;

    @Column(name = "image_blob_id")
    private String imageBlobId;

    @Enumerated(EnumType.STRING)
    @Column(name = "organization_status", nullable = false)
    private OrganizationStatus status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organization")
    private List<ClientOrganization> clientOrganizations;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invitation_organization_id", foreignKey = @ForeignKey(name = "fk_organization_invitation_organization_id"), updatable = false)
    private InvitationOrganization invitation;

    public Organization() {
        super();
    }

    public Organization(final String name, final String slug, final String imageBlobId, final OrganizationStatus status) {
        this.name = name;
        this.slug = slug;
        this.imageBlobId = imageBlobId;
        this.status = status;
    }

    public Organization(final String name, final String slug, final InvitationOrganization invitation) {
        this.name = name;
        this.slug = slug;
        this.status = OrganizationStatus.ACTIVE;
        this.invitation = invitation;
    }

    public Organization(final String name, final String slug, final String imageBlobId, final OrganizationStatus status, final List<ClientOrganization> clientOrganizations) {
        this.name = name;
        this.slug = slug;
        this.imageBlobId = imageBlobId;
        this.status = status;
        this.clientOrganizations = clientOrganizations;
    }

    //region Public methods
    public void grantClientOrganization(final ClientOrganization clientOrganization) {
        if (!containsClientOrganization(clientOrganization)) {
            mutableClientOrganization().add(clientOrganization);
        }
    }

    public List<ClientOrganization> getClientOrganizations() {
        if (clientOrganizations == null) {
            clientOrganizations = new ArrayList<>();
        }
        return Collections.unmodifiableList(clientOrganizations);
    }
    //endregion

    //region Utility methods
    private List<ClientOrganization> mutableClientOrganization() {
        if (clientOrganizations == null) {
            clientOrganizations = new ArrayList<>();
            return clientOrganizations;
        }
        return clientOrganizations;
    }

    private boolean containsClientOrganization(final ClientOrganization clientOrganization) {
        return immutableClientOrganizations().stream()
                .anyMatch(organization -> organization.equals(clientOrganization));
    }

    private List<ClientOrganization> immutableClientOrganizations() {
        return Optional.ofNullable(clientOrganizations)
                .map(Collections::unmodifiableList)
                .orElseGet(Collections::emptyList);
    }

    public Boolean hasBeenCreatedFromInvitation() {
        return !Objects.isNull(invitation);
    }
    //endregion

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Organization)) {
            return false;
        }
        final Organization that = (Organization) o;
        return new EqualsBuilder()
                .append(getUuid(), that.getUuid())
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
                .append("name", name)
                .append("slug", slug)
                .append("imageBlobId", imageBlobId)
                .append("status", status)
                .append("invitation", getIdOrNull(invitation))
                .toString();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public String getImageBlobId() {
        return imageBlobId;
    }

    public void setImageBlobId(final String imageBlobId) {
        this.imageBlobId = imageBlobId;
    }

    public OrganizationStatus getStatus() {
        return status;
    }

    public void setStatus(final OrganizationStatus status) {
        this.status = status;
    }

    public InvitationOrganization getInvitation() {
        return invitation;
    }
}
