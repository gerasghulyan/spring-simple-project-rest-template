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
 * Created by Arthur Asatryan.
 * Date: 10/18/19
 * Time: 5:06 PM
 */
public class FindUserByUuidAndOrganizationRequest extends AbstractRequestModel implements ValidatableRequest<UserErrorResponseModel> {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("organizationUuid")
    private String organizationUuid;

    public FindUserByUuidAndOrganizationRequest() {
    }

    public FindUserByUuidAndOrganizationRequest(final String uuid, final String organizationUuid) {
        this.uuid = uuid;
        this.organizationUuid = organizationUuid;
    }

    @Override
    public List<UserErrorResponseModel> validate() {
        final List<UserErrorResponseModel> errors = initializeNew();
        if (StringUtils.isBlank(uuid)) {
            errors.add(UserErrorResponseModel.MISSING_UUID);
        }
        if (StringUtils.isBlank(organizationUuid)) {
            errors.add(UserErrorResponseModel.MISSING_ORGANIZATION);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FindUserByUuidAndOrganizationRequest)) {
            return false;
        }
        final FindUserByUuidAndOrganizationRequest that = (FindUserByUuidAndOrganizationRequest) o;
        return new EqualsBuilder()
                .append(uuid, that.uuid)
                .append(organizationUuid, that.organizationUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uuid)
                .append(organizationUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("uuid", uuid)
                .append("organizationUuid", organizationUuid)
                .toString();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }

    public void setOrganizationUuid(final String organizationUuid) {
        this.organizationUuid = organizationUuid;
    }
}
