package com.vntana.core.service.organization.dto;

import com.vntana.core.service.common.dto.ServiceDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Created by Arman Gevorgyan.
 * Date: 12/26/19
 * Time: 3:37 PM
 */
public class UpdateOrganizationDto implements ServiceDto {

    private final String uuid;
    private final String imageId;
    private final String name;

    public UpdateOrganizationDto(final String uuid, final String imageId, final String name) {
        Assert.hasText(uuid, "The uuid should not be null or empty");
        Assert.hasText(name, "The name should not be null or empty");
        this.uuid = uuid;
        this.imageId = imageId;
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UpdateOrganizationDto)) {
            return false;
        }
        final UpdateOrganizationDto that = (UpdateOrganizationDto) o;
        return new EqualsBuilder()
                .append(uuid, that.uuid)
                .append(imageId, that.imageId)
                .append(name, that.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uuid)
                .append(imageId)
                .append(name)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("uuid", uuid)
                .append("imageId", imageId)
                .append("name", name)
                .toString();
    }

    public String getUuid() {
        return uuid;
    }

    public String getImageId() {
        return imageId;
    }

    public String getName() {
        return name;
    }
}
