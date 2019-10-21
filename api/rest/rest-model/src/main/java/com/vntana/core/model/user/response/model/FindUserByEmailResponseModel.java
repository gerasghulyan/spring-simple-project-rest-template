package com.vntana.core.model.user.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.ResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Arthur Asatryan.
 * Date: 10/18/19
 * Time: 5:08 PM
 */
public class FindUserByEmailResponseModel implements ResponseModel {

    @JsonProperty("exists")
    private boolean exists;

    public FindUserByEmailResponseModel() {
    }

    public FindUserByEmailResponseModel(final boolean exists) {
        this.exists = exists;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FindUserByEmailResponseModel)) {
            return false;
        }
        final FindUserByEmailResponseModel that = (FindUserByEmailResponseModel) o;
        return new EqualsBuilder()
                .append(exists, that.exists)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(exists)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("exists", exists)
                .toString();
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(final boolean exists) {
        this.exists = exists;
    }
}
