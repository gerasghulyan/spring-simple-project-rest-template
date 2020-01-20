package com.vntana.core.service.client.dto;

import com.vntana.core.service.common.dto.ServiceDto;
import org.apache.commons.lang3.StringUtils;
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

    private String imageBlobId;

    public CreateClientOrganizationDto() {
        super();
    }

    public CreateClientOrganizationDto(final String name,
                                       final String slug,
                                       final String imageBlobId,
                                       final String organizationUuid) {
        super();
        this.name = name;
        this.slug = slug;
        this.imageBlobId = imageBlobId;
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
                .append(imageBlobId, that.imageBlobId)
                .append(organizationUuid, that.organizationUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(name)
                .append(slug)
                .append(imageBlobId)
                .append(organizationUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("slug", slug)
                .append("imageBlobId", imageBlobId)
                .append("organizationUuid", organizationUuid)
                .toString();
    }

    public String getName() {
        return StringUtils.trim(name);
    }

    public String getSlug() {
        return StringUtils.trim(slug);
    }

    public String getImageBlobId() {
        return imageBlobId;
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }
}
