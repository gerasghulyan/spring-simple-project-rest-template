package com.vntana.core.domain.organization;

import com.vntana.core.domain.commons.AbstractUuidAwareDomainEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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

    public Organization() {
    }

    public Organization(final String name, final String slug) {
        this.name = name;
        this.slug = slug;
    }

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
                .toString();
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }
}
