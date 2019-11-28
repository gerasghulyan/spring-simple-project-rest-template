package com.vntana.core.domain.client;

import com.vntana.core.domain.commons.AbstractUuidAwareDomainEntity;
import com.vntana.core.domain.organization.Organization;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * Created by Arthur Asatryan.
 * Date: 10/8/19
 * Time: 5:05 PM
 */
@Entity
@Table(name = "client_organization", uniqueConstraints = {
        @UniqueConstraint(name = "uk_client_organization_slug_organization_id", columnNames = {"client_organization_slug", "organization_id"})
})
public class ClientOrganization extends AbstractUuidAwareDomainEntity {

    @Column(name = "client_organization_name", nullable = false)
    private String name;

    @Column(name = "client_organization_slug", nullable = false, updatable = false)
    private String slug;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false, foreignKey = @ForeignKey(name = "fk_organization_id"), updatable = false)
    private Organization organization;

    @Column(name = "image_id", nullable = false)
    private String imageId;

    public ClientOrganization() {
        super();
    }

    public ClientOrganization(final String name,
                              final String slug,
                              final String imageId,
                              final Organization organization) {
        super();
        this.name = name;
        this.slug = slug;
        this.imageId = imageId;
        this.organization = organization;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientOrganization)) {
            return false;
        }
        final ClientOrganization that = (ClientOrganization) o;
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
                .append("imageId", imageId)
                .append("slug", slug)
                .toString();
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public String getImageId() {
        return imageId;
    }

    public Organization getOrganization() {
        return organization;
    }
}
