package com.vntana.core.service.organization.dto;

import com.vntana.core.domain.user.UserRole;
import com.vntana.core.service.common.dto.ServiceDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Created by Manuk Gharslyan.
 * Date: 3/6/2020
 * Time: 5:53 PM
 */
public class GetUserOrganizationsByUserUuidAndRoleDto implements ServiceDto {

    private final String userUuid;
    private final UserRole userRole;

    public GetUserOrganizationsByUserUuidAndRoleDto(final String userUuid, final UserRole userRole) {
        Assert.hasText(userUuid, "The userUuid should not be null or empty");
        Assert.notNull(userRole, "The userUuid should not be null");
        this.userUuid = userUuid;
        this.userRole = userRole;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof GetUserOrganizationsByUserUuidAndRoleDto)) return false;

        final GetUserOrganizationsByUserUuidAndRoleDto that = (GetUserOrganizationsByUserUuidAndRoleDto) o;

        return new EqualsBuilder()
                .append(userUuid, that.userUuid)
                .append(userRole, that.userRole)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(userUuid)
                .append(userRole)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("userUuid", userUuid)
                .append("userRole", userRole)
                .toString();
    }

    public String getUserUuid() {
        return userUuid;
    }

    public UserRole getUserRole() {
        return userRole;
    }
}