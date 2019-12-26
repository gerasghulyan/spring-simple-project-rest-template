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
    private String imageId;

    public CreateOrganizationDto() {
        super();
    }

    public CreateOrganizationDto(final String name, final String slug, final String imageId) {
        Assert.hasText(name, "The name should not be null or empty");
        Assert.hasText(slug, "The slug should not be null or empty");
        this.name = name;
        this.slug = slug;
        this.imageId = imageId;
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
                .append(imageId, that.imageId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(name)
                .append(slug)
                .append(imageId)
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
        return StringUtils.trim(name);
    }

    public String getSlug() {
        return StringUtils.trim(slug);
    }

    public String getImageId() {
        return imageId;
    }
}
