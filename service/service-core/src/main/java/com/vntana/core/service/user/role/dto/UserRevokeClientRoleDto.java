package com.vntana.core.service.user.role.dto;

import com.vntana.commons.service.dto.ServiceDto;
import com.vntana.core.domain.user.UserRole;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Created by Vardan Aivazian
 * Date: 03.11.2020
 * Time: 15:36
 */
public class UserRevokeClientRoleDto implements ServiceDto {

    private final String userUuid;

    private final String clientOrganizationUuid;

    private final UserRole clientRole;

    public UserRevokeClientRoleDto(final String userUuid, final String clientOrganizationUuid, final UserRole clientRole) {
        Assert.hasText(userUuid, "The user uuid should not be null or empty");
        Assert.hasText(clientOrganizationUuid, "The client organization uuid should not be null or empty");
        Assert.notNull(clientRole, "The client role should not be null");
        Assert.isTrue(clientRole.hasClientAbility(), "The UserRole should be client related role");
        this.clientRole = clientRole;
        this.userUuid = userUuid;
        this.clientOrganizationUuid = clientOrganizationUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserRevokeClientRoleDto)) {
            return false;
        }
        final UserRevokeClientRoleDto that = (UserRevokeClientRoleDto) o;
        return new EqualsBuilder()
                .append(userUuid, that.userUuid)
                .append(clientOrganizationUuid, that.clientOrganizationUuid)
                .append(clientRole, that.clientRole)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(userUuid)
                .append(clientOrganizationUuid)
                .append(clientRole)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("userUuid", userUuid)
                .append("clientOrganizationUuid", clientOrganizationUuid)
                .append("clientRole", clientRole)
                .toString();
    }

    public String getUserUuid() {
        return userUuid;
    }

    public String getClientOrganizationUuid() {
        return clientOrganizationUuid;
    }

    public UserRole getClientRole() {
        return clientRole;
    }
}
