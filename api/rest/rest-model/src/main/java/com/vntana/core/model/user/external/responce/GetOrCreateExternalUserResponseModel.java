package com.vntana.core.model.user.external.responce;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by Diana Gevorgyan
 * Date: 7/14/2021
 * Time: 5:12 PM
 */
public class GetOrCreateExternalUserResponseModel extends AbstractUuidAwareResponseModel {
    
    @JsonProperty("fullName")
    private String fullName;

    public GetOrCreateExternalUserResponseModel() {
    }

    public GetOrCreateExternalUserResponseModel(final String uuid, final String fullName) {
        super(uuid);
        this.fullName = fullName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GetOrCreateExternalUserResponseModel)) {
            return false;
        }
        final GetOrCreateExternalUserResponseModel that = (GetOrCreateExternalUserResponseModel) o;
        return new EqualsBuilder()
                .append(fullName, that.fullName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(fullName)
                .toHashCode();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }
}
