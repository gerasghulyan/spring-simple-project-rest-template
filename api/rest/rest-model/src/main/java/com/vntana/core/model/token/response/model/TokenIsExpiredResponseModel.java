package com.vntana.core.model.token.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.ResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 10:20 AM
 */
public class TokenIsExpiredResponseModel implements ResponseModel {

    @JsonProperty("isExpired")
    private Boolean isExpired;

    public TokenIsExpiredResponseModel() {
        super();
    }

    public TokenIsExpiredResponseModel(final Boolean isExpired) {
        this.isExpired = isExpired;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TokenIsExpiredResponseModel)) {
            return false;
        }
        final TokenIsExpiredResponseModel that = (TokenIsExpiredResponseModel) o;
        return new EqualsBuilder()
                .append(isExpired, that.isExpired)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(isExpired)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("isExpired", isExpired)
                .toString();
    }

    public Boolean getExpired() {
        return isExpired;
    }
}
