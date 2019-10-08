package com.vntana.core.service.user.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Arthur Asatryan.
 * Date: 10/3/19
 * Time: 6:38 PM
 */
public class UserCreateDto {

    private final String firstName;
    private final String secondName;

    public UserCreateDto(final String firstName, final String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
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
                .append(firstName, that.firstName)
                .append(secondName, that.secondName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(firstName)
                .append(secondName)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("firstName", firstName)
                .append("secondName", secondName)
                .toString();
    }
}
