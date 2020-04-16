package com.vntana.core.service.organization.dto;

import com.vntana.core.service.common.dto.ServiceDto;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Created by Arthur Asatryan.
 * Date: 10/8/19
 * Time: 5:18 PM
 */
public class CreateOrganizationDto implements ServiceDto {

    private String name;
    private String slug;
    private String imageBlobId;

    public CreateOrganizationDto() {
        super();
    }

    public CreateOrganizationDto(final String name, final String slug, final String imageBlobId) {
        Assert.hasText(name, "The name should not be null or empty");
        Assert.hasText(slug, "The slug should not be null or empty");
        this.name = name;
        this.slug = slug;
        this.imageBlobId = imageBlobId;
    }

    public CreateOrganizationDto(final String name, final String slug) {
        Assert.hasText(name, "The name should not be null or empty");
        Assert.hasText(slug, "The slug should not be null or empty");
        this.name = name;
        this.slug = slug;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateOrganizationDto)) {
            return false;
        }
        final CreateOrganizationDto that = (CreateOrganizationDto) o;
        return new EqualsBuilder()
                .append(name, that.name)
                .append(slug, that.slug)
                .append(imageBlobId, that.imageBlobId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(name)
                .append(slug)
                .append(imageBlobId)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("slug", slug)
                .append("imageBlobId", imageBlobId)
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
}
