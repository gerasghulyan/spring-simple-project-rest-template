package com.vntana.core.domain.organization;

import com.vntana.commons.persistence.domain.AbstractUuidAwareDomainEntity;
import com.vntana.core.domain.client.ClientOrganization;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by Arthur Asatryan.
 * Date: 10/15/19
 * Time: 3:40 PM
 */
@Entity
@Table(name = "organization")
public class Organization extends AbstractUuidAwareDomainEntity {

    @Column(name = "organization_name", nullable = false)
    private String name;

    @Column(name = "organization_slug", nullable = false, updatable = false, unique = true)
    private String slug;

    @Column(name = "image_id")
    private String imageId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organization")
    private List<ClientOrganization> clientOrganizations;

    public Organization() {
    }

    public Organization(final String name, final String slug, final String imageId) {
        this.name = name;
        this.slug = slug;
        this.imageId = imageId;
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
                .append("imageId", imageId)
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

    public String getImageId() {
        return imageId;
    }

    public void setImageId(final String imageId) {
        this.imageId = imageId;
    }
}
