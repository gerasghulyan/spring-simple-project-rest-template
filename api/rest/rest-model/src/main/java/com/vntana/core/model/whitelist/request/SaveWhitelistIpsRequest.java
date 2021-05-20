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
import java.util.stream.Collectors;

/**
 * Created by Arman Gevorgyan.
 * Date: 11/29/19
 * Time: 11:16 AM
 */
public class SaveWhitelistIpsRequest extends AbstractRequestModel implements ValidatableRequest<WhitelistIpErrorResponseModel> {

    @JsonProperty("organizationUuid")
    private String organizationUuid;

    @JsonProperty("whitelistIps")
    private List<CreateOrUpdateWhitelistIpItemRequestModel> whitelistIps;
    
    @JsonProperty("whitelistType")
    private WhitelistTypeModel type;

    private SaveWhitelistIpsRequest() {
        super();
    }

    public SaveWhitelistIpsRequest(final String organizationUuid, final List<CreateOrUpdateWhitelistIpItemRequestModel> whitelistIps, WhitelistTypeModel type) {
        super();
        this.organizationUuid = organizationUuid;
        this.whitelistIps = whitelistIps;
        this.type = type;
    }

    @Override
    public List<WhitelistIpErrorResponseModel> validate() {
        if (StringUtils.isEmpty(organizationUuid)) {
            return Collections.singletonList(WhitelistIpErrorResponseModel.MISSING_ORGANIZATION_UUID);
        }
        if (whitelistIps == null) {
            return Collections.singletonList(WhitelistIpErrorResponseModel.MISSING_WHITELIST_IPS);
        }
        if (type == null) {
            return Collections.singletonList(WhitelistIpErrorResponseModel.MISSING_WHITELIST_TYPE);
        }
        return whitelistIps.parallelStream()
                .flatMap(ip -> ip.validate().stream())
                .distinct()
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SaveWhitelistIpsRequest)) {
            return false;
        }
        final SaveWhitelistIpsRequest that = (SaveWhitelistIpsRequest) o;
        return new EqualsBuilder()
                .append(organizationUuid, that.organizationUuid)
                .append(whitelistIps, that.whitelistIps)
                .append(type, that.type)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(organizationUuid)
                .append(whitelistIps)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("organizationUuid", organizationUuid)
                .append("whitelistIps", whitelistIps)
                .append("type", type)
                .toString();
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }

    public void setOrganizationUuid(final String organizationUuid) {
        this.organizationUuid = organizationUuid;
    }

    public List<CreateOrUpdateWhitelistIpItemRequestModel> getWhitelistIps() {
        return whitelistIps;
    }

    public void setWhitelistIps(final List<CreateOrUpdateWhitelistIpItemRequestModel> whitelistIps) {
        this.whitelistIps = whitelistIps;
    }

    public WhitelistTypeModel getType() {
        return type;
    }

    public void setType(final WhitelistTypeModel type) {
        this.type = type;
    }
}
