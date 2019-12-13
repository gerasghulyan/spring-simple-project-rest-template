package com.vntana.core.model.user.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.ResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Arman Gevorgyan.
 * Date: 12/12/19
 * Time: 5:29 PM
 */
public class ResetUserPasswordResponseModel implements ResponseModel {

    @JsonProperty("email")
    private String email;

    private ResetUserPasswordResponseModel() {
        super();
    }

    public ResetUserPasswordResponseModel(final String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResetUserPasswordResponseModel)) {
            return false;
        }
        final ResetUserPasswordResponseModel that = (ResetUserPasswordResponseModel) o;
        return new EqualsBuilder()
                .append(email, that.email)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(email)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("email", email)
                .toString();
    }
}
