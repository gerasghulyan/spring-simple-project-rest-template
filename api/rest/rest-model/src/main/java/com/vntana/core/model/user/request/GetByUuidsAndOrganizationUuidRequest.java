package com.vntana.core.model.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;
import java.util.Set;

/**
 * Created by Vardan Aivazian
 * Date: 30.09.2020
 * Time: 11:35
 */
public class GetByUuidsAndOrganizationUuidRequest extends AbstractRequestModel implements ValidatableRequest<UserErrorResponseModel> {

    @JsonProperty("uuids")
    private Set<String> uuids;

    @JsonProperty("organizationUuid")
    private String organizationUuid;

    public GetByUuidsAndOrganizationUuidRequest() {
        super();
    }

    public GetByUuidsAndOrganizationUuidRequest(final Set<String> uuids, final String organizationUuid) {
        this.uuids = uuids;
        this.organizationUuid = organizationUuid;
    }

    @Override
    public List<UserErrorResponseModel> validate() {
        final List<UserErrorResponseModel> errors = initializeNew();
        if (CollectionUtils.isEmpty(uuids)) {
            errors.add(UserErrorResponseModel.MISSING_UUIDS);
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
        if (!(o instanceof GetByUuidsAndOrganizationUuidRequest)) {
            return false;
        }
        final GetByUuidsAndOrganizationUuidRequest that = (GetByUuidsAndOrganizationUuidRequest) o;
        return new EqualsBuilder()
                .append(uuids, that.uuids)
                .append(organizationUuid, that.organizationUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uuids)
                .append(organizationUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("uuids", uuids)
                .append("organizationUuid", organizationUuid)
                .toString();
    }

    public Set<String> getUuids() {
        return uuids;
    }

    public void setUuids(final Set<String> uuids) {
        this.uuids = uuids;
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }

    public void setOrganizationUuid(final String organizationUuid) {
        this.organizationUuid = organizationUuid;
    }
}
