package com.vntana.core.service.client.dto;

import com.vntana.core.service.common.dto.ServiceDto;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Geras Ghulyan.
 * Date: 10/8/19
 * Time: 5:18 PM
 */
public class UpdateClientOrganizationDto implements ServiceDto {

    private String uuid;
    private String name;
    private String imageId;

    public UpdateClientOrganizationDto() {
        super();
    }

    public UpdateClientOrganizationDto(
            final String uuid,
            final String name,
            final String imageId) {
        super();
        this.uuid = uuid;
        this.name = name;
        this.imageId = imageId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UpdateClientOrganizationDto)) {
            return false;
        }
        final UpdateClientOrganizationDto that = (UpdateClientOrganizationDto) o;
        return new EqualsBuilder()
                .append(uuid, that.uuid)
                .append(name, that.name)
                .append(imageId, that.imageId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uuid)
                .append(name)
                .append(imageId)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("uuid", uuid)
                .append("name", name)
                .append("imageId", imageId)
                .toString();
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return StringUtils.trim(name);
    }

    public String getImageId() {
        return imageId;
    }

}
