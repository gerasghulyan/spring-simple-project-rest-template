package com.vntana.core.service.user.dto;

import com.vntana.core.service.common.dto.ServiceDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.util.Assert;

/**
 * Created by Manuk Gharslyan.
 * Date: 1/23/2020
 * Time: 3:37 PM
 */
public class UpdateUserDto implements ServiceDto {

    private String uuid;

    private String imageBlobId;

    private String fullName;

    public UpdateUserDto() {
    }

    public UpdateUserDto(final String uuid, final String imageBlobId, final String fullName) {
        Assert.hasText(uuid, "The uuid should not be null or empty");
        Assert.hasText(fullName, "The full name should not be null or empty");
        this.uuid = uuid;
        this.imageBlobId = imageBlobId;
        this.fullName = fullName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof UpdateUserDto)) return false;

        final UpdateUserDto that = (UpdateUserDto) o;

        return new EqualsBuilder()
                .append(uuid, that.uuid)
                .append(imageBlobId, that.imageBlobId)
                .append(fullName, that.fullName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uuid)
                .append(imageBlobId)
                .append(fullName)
                .toHashCode();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public String getImageBlobId() {
        return imageBlobId;
    }

    public void setImageBlobId(final String imageBlobId) {
        this.imageBlobId = imageBlobId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }
}