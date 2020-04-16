package com.vntana.core.model.user.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.ResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Arman Gevorgyan.
 * Date: 4/6/20
 * Time: 10:13 AM
 */
public class ExistsUserByEmailResponseModel implements ResponseModel {

    @JsonProperty("exists")
    private Boolean exists;

    public ExistsUserByEmailResponseModel() {
        super();
    }

    public ExistsUserByEmailResponseModel(final Boolean exists) {
        super();
        this.exists = exists;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExistsUserByEmailResponseModel)) {
            return false;
        }
        final ExistsUserByEmailResponseModel that = (ExistsUserByEmailResponseModel) o;
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

    public Boolean getExists() {
        return exists;
    }
}
