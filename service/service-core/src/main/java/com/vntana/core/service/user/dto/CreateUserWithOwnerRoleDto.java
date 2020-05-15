package com.vntana.core.service.user.dto;

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
public class CreateUserWithOwnerRoleDto implements ServiceDto {

    private final String fullName;

    private final String email;

    private final String password;

    private final String organizationUuid;

    public CreateUserWithOwnerRoleDto(final String fullName, final String email, final String password, final String organizationUuid) {
        Assert.hasText(fullName, "The user full name should not be null or empty");
        Assert.hasText(email, "The email should not be null or empty");
        Assert.hasText(password, "The user password should not be null or empty");
        Assert.hasText(organizationUuid, "The organization uuid should not be null or empty");
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.organizationUuid = organizationUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateUserWithOwnerRoleDto)) {
            return false;
        }
        final CreateUserWithOwnerRoleDto that = (CreateUserWithOwnerRoleDto) o;
        return new EqualsBuilder()
                .append(fullName, that.fullName)
                .append(email, that.email)
                .append(password, that.password)
                .append(organizationUuid, that.organizationUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(fullName)
                .append(email)
                .append(password)
                .append(organizationUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("fullName", fullName)
                .append("email", email)
                .append("organizationUuid", organizationUuid)
                .append("password", "***")
                .toString();
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }
}
