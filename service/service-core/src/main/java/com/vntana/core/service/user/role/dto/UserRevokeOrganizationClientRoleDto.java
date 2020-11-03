package com.vntana.core.service.user.role.dto;

import com.vntana.commons.service.dto.ServiceDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Created by Vardan Aivazian
 * Date: 03.11.2020
 * Time: 15:36
 */
public class UserRevokeOrganizationClientRoleDto implements ServiceDto {

    private final String userUuid;

    private final String organizationUuid;

    private final UserClientRole clientRole;

    public UserRevokeOrganizationClientRoleDto(final String userUuid, final String organizationUuid, final UserClientRole clientRole) {
        Assert.hasText(userUuid, "The user uuid should not be null or empty");
        Assert.hasText(organizationUuid, "The organization uuid should not be null or empty");
        Assert.notNull(clientRole, "The client role should not be null");
        this.clientRole = clientRole;
        this.userUuid = userUuid;
        this.organizationUuid = organizationUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserRevokeOrganizationClientRoleDto)) {
            return false;
        }
        final UserRevokeOrganizationClientRoleDto that = (UserRevokeOrganizationClientRoleDto) o;
        return new EqualsBuilder()
                .append(userUuid, that.userUuid)
                .append(organizationUuid, that.organizationUuid)
                .append(clientRole, that.clientRole)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(userUuid)
                .append(organizationUuid)
                .append(clientRole)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("userUuid", userUuid)
                .append("organizationUuid", organizationUuid)
                .append("clientRole", clientRole)
                .toString();
    }

    public String getUserUuid() {
        return userUuid;
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }

    public UserClientRole getClientRole() {
        return clientRole;
    }
}
