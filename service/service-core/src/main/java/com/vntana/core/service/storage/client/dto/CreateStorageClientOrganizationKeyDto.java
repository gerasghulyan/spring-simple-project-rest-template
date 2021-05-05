package com.vntana.core.service.storage.client.dto;

import com.vntana.commons.service.dto.ServiceDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Created by Geras Ghulyan
 * Date: 30-Apr-21
 * Time: 15:25
 */
public class CreateStorageClientOrganizationKeyDto implements ServiceDto {

    private final String clientUuid;

    public CreateStorageClientOrganizationKeyDto(final String clientUuid) {
        Assert.hasText(clientUuid, "The clientUuid should not be null or empty");
        this.clientUuid = clientUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateStorageClientOrganizationKeyDto)) {
            return false;
        }
        final CreateStorageClientOrganizationKeyDto that = (CreateStorageClientOrganizationKeyDto) o;
        return new EqualsBuilder()
                .append(clientUuid, that.clientUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(clientUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("clientUuid", clientUuid)
                .toString();
    }

    public String getClientUuid() {
        return clientUuid;
    }
    
}
