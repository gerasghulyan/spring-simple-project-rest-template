package com.vntana.core.service.client.dto;

import com.vntana.commons.service.dto.ServiceDto;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static com.vntana.commons.utils.DataSanitizerUtils.mask;

/**
 * Created by Geras Ghulyan.
 * Date: 10/8/19
 * Time: 5:18 PM
 */
public class UpdateClientOrganizationDto implements ServiceDto {

    private String uuid;
    private String name;
    private String imageBlobId;

    public UpdateClientOrganizationDto() {
        super();
    }

    public UpdateClientOrganizationDto(
            final String uuid,
            final String name,
            final String imageBlobId) {
        super();
        this.uuid = uuid;
        this.name = name;
        this.imageBlobId = imageBlobId;
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
                .append(imageBlobId, that.imageBlobId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uuid)
                .append(name)
                .append(imageBlobId)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("uuid", uuid)
                .append("name", name)
                .append("imageBlobId", mask(imageBlobId))
                .toString();
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return StringUtils.trim(name);
    }

    public String getImageBlobId() {
        return imageBlobId;
    }

}
