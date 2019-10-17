package com.vntana.core.service.client.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Arthur Asatryan.
 * Date: 10/8/19
 * Time: 5:18 PM
 */
public class CreateClientOrganizationDto {

    private String name;

    private String slug;

    private String organizationUuid;

    public CreateClientOrganizationDto() {
    }

    public CreateClientOrganizationDto(final String name, final String slug, final String organizationUuid) {
        this.name = name;
        this.slug = slug;
        this.organizationUuid = organizationUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateClientOrganizationDto)) {
            return false;
        }
        final CreateClientOrganizationDto that = (CreateClientOrganizationDto) o;
        return new EqualsBuilder()
                .append(name, that.name)
                .append(slug, that.slug)
                .append(organizationUuid, that.organizationUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(name)
                .append(slug)
                .append(organizationUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("slug", slug)
                .append("organizationUuid", organizationUuid)
                .toString();
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }
}
