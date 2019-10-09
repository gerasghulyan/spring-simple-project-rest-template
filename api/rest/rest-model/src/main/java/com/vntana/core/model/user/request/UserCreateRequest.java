package com.vntana.core.model.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.core.api.models.request.RequestModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Arthur Asatryan.
 * Date: 10/1/19
 * Time: 6:40 PM
 */
public class UserCreateRequest implements RequestModel {

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("secondName")
    private String secondName;

    public UserCreateRequest() {
        super();
    }

    public UserCreateRequest(final String firstName, final String secondName) {
        super();
        this.firstName = firstName;
        this.secondName = secondName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserCreateRequest)) {
            return false;
        }
        final UserCreateRequest that = (UserCreateRequest) o;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(final String secondName) {
        this.secondName = secondName;
    }
}
