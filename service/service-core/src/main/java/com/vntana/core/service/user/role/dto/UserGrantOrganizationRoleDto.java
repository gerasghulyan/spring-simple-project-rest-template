package com.vntana.core.service.user.role.dto;

import com.vntana.core.service.common.dto.ServiceDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Created by Arthur Asatryan.
 * Date: 10/3/19
 * Time: 6:38 PM
 */
public class UserGrantOrganizationRoleDto implements ServiceDto {

    private final String userUuid;

    private final String organizationUuid;

    public UserGrantOrganizationRoleDto(final String userUuid, final String organizationUuid) {
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
        if (!(o instanceof UserGrantOrganizationRoleDto)) {
            return false;
        }
        final UserGrantOrganizationRoleDto that = (UserGrantOrganizationRoleDto) o;
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
