package com.vntana.core.service.user.dto;

import com.vntana.core.domain.user.UserRole;
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

    private final String uuid;

    private final String organizationUuid;

    private final UserRole role;

    public UserGrantOrganizationRoleDto(final String uuid, final String organizationUuid, final UserRole role) {
        Assert.hasText(uuid, "The user uuid should not be null or empty");
        Assert.hasText(organizationUuid, "The organization uuid should not be null or empty");
        Assert.notNull(role, "The user role should not be null");
        this.uuid = uuid;
        this.organizationUuid = organizationUuid;
        this.role = role;
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
                .append(uuid, that.uuid)
                .append(organizationUuid, that.organizationUuid)
                .append(role, that.role)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uuid)
                .append(organizationUuid)
                .append(role)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("uuid", uuid)
                .append("organizationUuid", organizationUuid)
                .append("role", role)
                .toString();
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }

    public UserRole getRole() {
        return role;
    }

    public String getUuid() {
        return uuid;
    }
}
