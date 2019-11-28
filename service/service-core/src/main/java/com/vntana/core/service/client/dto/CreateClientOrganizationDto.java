package com.vntana.core.service.client.dto;

import com.vntana.core.service.common.dto.ServiceDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Arthur Asatryan.
 * Date: 10/8/19
 * Time: 5:18 PM
 */
public class CreateClientOrganizationDto implements ServiceDto {

    private String name;

    private String slug;

    private String organizationUuid;

    private String imageId;

    public CreateClientOrganizationDto() {
        super();
    }

    public CreateClientOrganizationDto(final String name,
                                       final String slug,
                                       final String imageId,
                                       final String organizationUuid) {
        super();
        this.name = name;
        this.slug = slug;
        this.imageId = imageId;
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
                .append(imageId, that.imageId)
                .append(organizationUuid, that.organizationUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(name)
                .append(slug)
                .append(imageId)
                .append(organizationUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("slug", slug)
                .append("imageId", imageId)
                .append("organizationUuid", organizationUuid)
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

    public String getOrganizationUuid() {
        return organizationUuid;
    }
}
