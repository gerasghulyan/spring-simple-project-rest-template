package com.vntana.core.service.user.dto;

import com.vntana.commons.service.dto.ServiceDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/15/20
 * Time: 4:22 PM
 */
public class CreateUserDto implements ServiceDto {

    private final String fullName;

    private final String email;

    private final String password;

    public CreateUserDto(final String fullName, final String email, final String password) {
        Assert.hasText(fullName, "The user full name should not be null or empty");
        Assert.hasText(email, "The email should not be null or empty");
        Assert.hasText(password, "The user password should not be null or empty");
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateUserDto)) {
            return false;
        }
        final CreateUserDto that = (CreateUserDto) o;
        return new EqualsBuilder()
                .append(fullName, that.fullName)
                .append(email, that.email)
                .append(password, that.password)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(fullName)
                .append(email)
                .append(password)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("fullName", fullName)
                .append("email", email)
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
}
