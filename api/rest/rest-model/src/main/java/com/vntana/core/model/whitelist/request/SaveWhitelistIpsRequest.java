package com.vntana.core.model.whitelist.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.whitelist.error.WhitelistIpErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.validator.routines.InetAddressValidator;

import java.util.Collections;
import java.util.List;

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

    private SaveWhitelistIpsRequest() {
        super();
    }

    public SaveWhitelistIpsRequest(final String organizationUuid, final List<CreateOrUpdateWhitelistIpItemRequestModel> whitelistIps) {
        super();
        this.organizationUuid = organizationUuid;
        this.whitelistIps = whitelistIps;
    }

    @Override
    public List<WhitelistIpErrorResponseModel> validate() {
        if (StringUtils.isEmpty(organizationUuid)) {
            return Collections.singletonList(WhitelistIpErrorResponseModel.MISSING_ORGANIZATION_UUID);
        }
        if (whitelistIps == null) {
            return Collections.singletonList(WhitelistIpErrorResponseModel.MISSING_WHITELIST_IPS);
        }
        final boolean containsWrongIp = whitelistIps.stream().map(CreateOrUpdateWhitelistIpItemRequestModel::getIp).anyMatch(ip -> {
            final boolean validInet4Address = InetAddressValidator.getInstance().isValidInet4Address(ip);
            final boolean validInet6Address = InetAddressValidator.getInstance().isValidInet6Address(ip);
            return !(validInet4Address || validInet6Address);
        });
        if (containsWrongIp) {
            return Collections.singletonList(WhitelistIpErrorResponseModel.INVALID_IP);
        }
        return Collections.emptyList();
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
}
