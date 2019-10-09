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

    public CreateClientOrganizationDto() {
    }

    public CreateClientOrganizationDto(final String name, final String slug) {
        this.name = name;
        this.slug = slug;
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
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(name)
                .append(slug)
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
