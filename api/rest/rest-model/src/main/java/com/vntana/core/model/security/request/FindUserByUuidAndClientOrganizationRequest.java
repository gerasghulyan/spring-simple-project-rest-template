package com.vntana.core.model.security.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by Manuk Gharslyan.
 * Date: 10/18/19
 * Time: 5:06 PM
 */
public class FindUserByUuidAndClientOrganizationRequest extends AbstractRequestModel implements ValidatableRequest<UserErrorResponseModel> {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("clientUuid")
    private String clientUuid;

    public FindUserByUuidAndClientOrganizationRequest() {
        super();
    }

    public FindUserByUuidAndClientOrganizationRequest(final String uuid, final String clientUuid) {
        this.uuid = uuid;
        this.clientUuid = clientUuid;
    }

    @Override
    public List<UserErrorResponseModel> validate() {
        final List<UserErrorResponseModel> errors = initializeNew();
        if (StringUtils.isBlank(uuid)) {
            errors.add(UserErrorResponseModel.MISSING_UUID);
        }
        if (StringUtils.isBlank(clientUuid)) {
            errors.add(UserErrorResponseModel.MISSING_CLIENT);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FindUserByUuidAndClientOrganizationRequest)) {
            return false;
        }
        final FindUserByUuidAndClientOrganizationRequest that = (FindUserByUuidAndClientOrganizationRequest) o;
        return new EqualsBuilder()
                .append(uuid, that.uuid)
                .append(clientUuid, that.clientUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uuid)
                .append(clientUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("uuid", uuid)
                .append("clientUuid", clientUuid)
                .toString();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public String getClientUuid() {
        return clientUuid;
    }

    public void setClientUuid(final String clientUuid) {
        this.clientUuid = clientUuid;
    }
}