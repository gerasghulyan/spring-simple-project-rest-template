package com.project.sample.commons.persistence.domain.organization;

import com.project.sample.commons.persistence.domain.AbstractUuidAwareDomainEntity;
import com.project.sample.commons.persistence.domain.organization.status.OrganizationStatus;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * Created by Geras Ghulyan.
 * Date: 10/15/19
 * Time: 3:40 PM
 */
@Entity
@Table(name = "organization", uniqueConstraints = {
        @UniqueConstraint(name = "uk_organization_uuid", columnNames = {"uuid"})
})
public class Organization extends AbstractUuidAwareDomainEntity {

    @Column(name = "organization_name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "organization_status", nullable = false)
    private OrganizationStatus status;

    public Organization() {
        super();
    }

    public Organization(final String name, final OrganizationStatus status) {
        this.name = name;
        this.status = status;
    }

    public Organization(final String name) {
        this.name = name;
        this.status = OrganizationStatus.ACTIVE;
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
                .append("status", status)
                .toString();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public OrganizationStatus getStatus() {
        return status;
    }

    public void setStatus(final OrganizationStatus status) {
        this.status = status;
    }
}
