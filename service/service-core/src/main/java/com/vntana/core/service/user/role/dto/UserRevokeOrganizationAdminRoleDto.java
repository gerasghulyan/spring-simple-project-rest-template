package com.vntana.core.service.user.role.dto;

import com.vntana.commons.service.dto.ServiceDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/8/20
 * Time: 5:47 PM
 */
public class UserRevokeOrganizationAdminRoleDto implements ServiceDto {

    private final String userUuid;

    private final String organizationUuid;

    public UserRevokeOrganizationAdminRoleDto(final String userUuid, final String organizationUuid) {
        Assert.hasText(userUuid, "The user uuid should not be null or empty");
        Assert.hasText(organizationUuid, "The organization uuid should not be null or empty");
        this.userUuid = userUuid;
        this.organizationUuid = organizationUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserRevokeOrganizationAdminRoleDto)) {
            return false;
        }
        final UserRevokeOrganizationAdminRoleDto that = (UserRevokeOrganizationAdminRoleDto) o;
        return new EqualsBuilder()
                .append(userUuid, that.userUuid)
                .append(organizationUuid, that.organizationUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(userUuid)
                .append(organizationUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("uuid", userUuid)
                .append("organizationUuid", organizationUuid)
                .toString();
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }

    public String getUserUuid() {
        return userUuid;
    }
}
