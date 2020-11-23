package com.vntana.core.model.token.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.ResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by Diana Gevorgyan
 * Date: 11/19/20
 * Time: 4:03 PM
 */
public class TokenBulkCreateResponseModel implements ResponseModel {

    @JsonProperty("uuids")
    private List<String> uuids;

    public TokenBulkCreateResponseModel() {
        super();
    }

    public TokenBulkCreateResponseModel(final List<String> uuids) {
        this.uuids = uuids;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TokenBulkCreateResponseModel)) {
            return false;
        }
        final TokenBulkCreateResponseModel that = (TokenBulkCreateResponseModel) o;
        return new EqualsBuilder()
                .append(uuids, that.uuids)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uuids)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("uuids", uuids)
                .toString();
    }

    public List<String> getUuids() {
        return uuids;
    }

    public void setUuids(final List<String> uuids) {
        this.uuids = uuids;
    }
}
