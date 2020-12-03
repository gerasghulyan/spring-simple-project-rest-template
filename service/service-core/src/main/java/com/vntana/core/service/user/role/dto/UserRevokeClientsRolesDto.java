package com.vntana.core.service.user.role.dto;

import com.vntana.commons.service.dto.ServiceDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Manuk Gharslyan
 * Date: 03.11.2020
 * Time: 15:36
 */
public class UserRevokeClientsRolesDto implements ServiceDto {

    private final String userUuid;
    private final List<String> clientUuids;

    public UserRevokeClientsRolesDto(final String userUuid, final List<String> clientUuids) {
        Assert.hasText(userUuid, "The user uuid should not be null or empty");
        Assert.notEmpty(clientUuids, "The clients uuids should not be null or empty");
        this.userUuid = userUuid;
        this.clientUuids = clientUuids;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserRevokeClientsRolesDto)) {
            return false;
        }
        final UserRevokeClientsRolesDto that = (UserRevokeClientsRolesDto) o;
        return new EqualsBuilder()
                .append(userUuid, that.userUuid)
                .append(clientUuids, that.clientUuids)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(userUuid)
                .append(clientUuids)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("userUuid", userUuid)
                .append("clientUuids", clientUuids)
                .toString();
    }

    public String getUserUuid() {
        return userUuid;
    }

    public List<String> getClientUuids() {
        return clientUuids.stream().distinct().collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }
}