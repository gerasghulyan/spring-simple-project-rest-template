package com.vntana.core.service.organization.dto;

import com.vntana.commons.service.dto.ServiceDto;
import com.vntana.core.domain.organization.status.OrganizationStatus;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * Created by Arman Gevorgyan.
 * Date: 12/26/19
 * Time: 3:37 PM
 */
public class UpdateOrganizationDto implements ServiceDto {

    private final String uuid;
    private final String imageBlobId;
    private final String name;
    private final OrganizationStatus status;

    public UpdateOrganizationDto(final String uuid, final String imageBlobId, final String name, final OrganizationStatus status) {
        Assert.hasText(uuid, "The uuid should not be null or empty");
        Assert.hasText(name, "The name should not be null or empty");
        this.uuid = uuid;
        this.imageBlobId = imageBlobId;
        this.name = name;
        this.status = status;
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
                .append(imageBlobId, that.imageBlobId)
                .append(name, that.name)
                .append(status, that.status)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uuid)
                .append(imageBlobId)
                .append(name)
                .append(status)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("uuid", uuid)
                .append("imageBlobId", imageBlobId)
                .append("name", name)
                .append("status", status)
                .toString();
    }

    public String getUuid() {
        return uuid;
    }

    public String getImageBlobId() {
        return imageBlobId;
    }

    public String getName() {
        return name;
    }

    public Optional<OrganizationStatus> getStatus() {
        return Optional.ofNullable(status);
    }
}
