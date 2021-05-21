package com.vntana.core.model.whitelist.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.whitelist.error.WhitelistIpErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collections;
import java.util.List;

/**
 * Created by Diana Gevorgyan
 * Date: 5/20/21
 * Time: 10:26 AM
 */
public class GetWhitelistIpsRequest extends AbstractRequestModel implements ValidatableRequest<WhitelistIpErrorResponseModel> {

    @JsonProperty("organizationUuid")
    private String organizationUuid;

    @JsonProperty("whitelistType")
    private WhitelistTypeModel type;

    public GetWhitelistIpsRequest() {
    }

    public GetWhitelistIpsRequest(final String organizationUuid, final WhitelistTypeModel type) {
        this.organizationUuid = organizationUuid;
        this.type = type;
    }

    @Override
    public List<WhitelistIpErrorResponseModel> validate() {
        if (StringUtils.isEmpty(organizationUuid)) {
            return Collections.singletonList(WhitelistIpErrorResponseModel.MISSING_ORGANIZATION_UUID);
        }
        if (type == null) {
            return Collections.singletonList(WhitelistIpErrorResponseModel.MISSING_WHITELIST_TYPE);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GetWhitelistIpsRequest)) {
            return false;
        }
        final GetWhitelistIpsRequest that = (GetWhitelistIpsRequest) o;
        return new EqualsBuilder()
                .append(organizationUuid, that.organizationUuid)
                .append(type, that.type)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(organizationUuid)
                .append(type)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("organizationUuid", organizationUuid)
                .append("type", type)
                .toString();
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }

    public void setOrganizationUuid(final String organizationUuid) {
        this.organizationUuid = organizationUuid;
    }

    public WhitelistTypeModel getType() {
        return type;
    }

    public void setType(final WhitelistTypeModel type) {
        this.type = type;
    }
}
