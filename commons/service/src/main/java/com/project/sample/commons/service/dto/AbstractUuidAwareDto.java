package com.project.sample.commons.service.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Created by Geras Ghulyan.
 * Date: 11/1/19
 * Time: 6:02 PM
 */
public abstract class AbstractUuidAwareDto implements ServiceDto {

    private final String uuid;

    public AbstractUuidAwareDto(final String uuid) {
        Assert.hasText(uuid, "The uuid should not be null or empty");
        this.uuid = uuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractUuidAwareDto)) {
            return false;
        }
        final AbstractUuidAwareDto that = (AbstractUuidAwareDto) o;
        return new EqualsBuilder()
                .append(uuid, that.uuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("uuid", uuid)
                .toString();
    }

    public String getUuid() {
        return uuid;
    }
}
