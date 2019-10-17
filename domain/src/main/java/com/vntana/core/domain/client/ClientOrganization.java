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
@Table(name = "client_organization")
public class ClientOrganization extends AbstractUuidAwareDomainEntity {

    @Column(name = "client_organization_name", nullable = false)
    private String name;

    @Column(name = "client_organization_slug", nullable = false, updatable = false, unique = true)
    private String slug;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false, foreignKey = @ForeignKey(name = "fk_organization_id"), updatable = false)
    private Organization organization;

    public ClientOrganization() {
    }

    public ClientOrganization(final String name, final String slug, final Organization organization) {
        this.name = name;
        this.slug = slug;
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
                .append("slug", slug)
                .toString();
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public Organization getOrganization() {
        return organization;
    }
}
