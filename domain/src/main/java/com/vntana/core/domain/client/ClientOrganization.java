package com.vntana.core.domain.client;

import com.vntana.core.domain.commons.AbstractUuidAwareDomainEntity;
import com.vntana.core.domain.user.UserClientOrganizationRole;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clientOrganization", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserClientOrganizationRole> clientOrganizations;

    public ClientOrganization() {
    }

    public ClientOrganization(final String name, final String slug) {
        this.name = name;
        this.slug = slug;
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
}
