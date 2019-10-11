package com.vntana.core.service.user.dto;

import com.vntana.core.domain.user.UserRole;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Arthur Asatryan.
 * Date: 10/3/19
 * Time: 6:38 PM
 */
public class UserCreateDto {

    private final String fullName;

    private final String email;

    private final String password;

    private final String clientOrganizationUuid;

    private final UserRole role;

    public UserCreateDto(final String fullName, final String email, final String password, final String clientOrganizationUuid, final UserRole role) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.clientOrganizationUuid = clientOrganizationUuid;
        this.role = role;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserCreateDto)) {
            return false;
        }
        final UserCreateDto that = (UserCreateDto) o;
        return new EqualsBuilder()
                .append(fullName, that.fullName)
                .append(email, that.email)
                .append(password, that.password)
                .append(clientOrganizationUuid, that.clientOrganizationUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(fullName)
                .append(email)
                .append(password)
                .append(clientOrganizationUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("fullName", fullName)
                .append("email", email)
                .append("clientOrganizationUuid", clientOrganizationUuid)
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

    public String getClientOrganizationUuid() {
        return clientOrganizationUuid;
    }

    public UserRole getRole() {
        return role;
    }
}
