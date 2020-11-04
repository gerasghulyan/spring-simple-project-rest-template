package com.vntana.core.service.user.role.dto;

import com.vntana.commons.service.dto.ServiceDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Created by Vardan Aivazian
 * Date: 03.11.2020
 * Time: 15:29
 */
public class UserGrantClientRoleDto implements ServiceDto {

    private final String userUuid;

    private final String clientOrganizationUuid;
    
    private final UserClientRole clientRole;

    public UserGrantClientRoleDto(final String userUuid, final String clientOrganizationUuid, final UserClientRole clientRole) {
        Assert.hasText(userUuid, "The user uuid should not be null or empty");
        Assert.hasText(clientOrganizationUuid, "The client organization uuid should not be null or empty");
        Assert.notNull(clientRole, "The client role should not be null");
        this.clientRole = clientRole;
        this.userUuid = userUuid;
        this.clientOrganizationUuid = clientOrganizationUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserGrantClientRoleDto)) {
            return false;
        }
        final UserGrantClientRoleDto that = (UserGrantClientRoleDto) o;
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

    public UserClientRole getClientRole() {
        return clientRole;
    }
}
