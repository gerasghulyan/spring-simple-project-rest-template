package com.vntana.core.model.user.external.responce;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.ResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Diana Gevorgyan
 * Date: 7/14/2021
 * Time: 5:12 PM
 */
public class GetOrCreateExternalUserResponseModel implements ResponseModel {

    @JsonProperty("userUuid")
    private String userUuid;
    
    @JsonProperty("externalUuid")
    private String externalUuid;

    public GetOrCreateExternalUserResponseModel() {
    }

    public GetOrCreateExternalUserResponseModel(final String userUuid, final String externalUuid) {
        this.userUuid = userUuid;
        this.externalUuid = externalUuid;
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
                .append(userUuid, that.userUuid)
                .append(externalUuid, that.externalUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(userUuid)
                .append(externalUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("userUuid", userUuid)
                .append("externalUuid", externalUuid)
                .toString();
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(final String userUuid) {
        this.userUuid = userUuid;
    }

    public String getExternalUuid() {
        return externalUuid;
    }

    public void setExternalUuid(final String externalUuid) {
        this.externalUuid = externalUuid;
    }
}
