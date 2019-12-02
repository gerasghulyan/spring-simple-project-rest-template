package com.vntana.core.model.whitelist.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Arman Gevorgyan.
 * Date: 11/28/19
 * Time: 6:17 PM
 */
public class GetWhitelistIpResponseModel extends AbstractUuidAwareResponseModel {

    @JsonProperty("label")
    private String label;

    @JsonProperty("ip")
    private String ip;

    @JsonProperty("organizationUuid")
    private String organizationUuid;

    public GetWhitelistIpResponseModel() {
        super();
    }

    public GetWhitelistIpResponseModel(final String uuid, final String label, final String ip, final String organizationUuid) {
        super(uuid);
        this.label = label;
        this.ip = ip;
        this.organizationUuid = organizationUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GetWhitelistIpResponseModel)) {
            return false;
        }
        final GetWhitelistIpResponseModel that = (GetWhitelistIpResponseModel) o;
        return new EqualsBuilder()
                .append(label, that.label)
                .append(ip, that.ip)
                .append(organizationUuid, that.organizationUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(label)
                .append(ip)
                .append(organizationUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("label", label)
                .append("ip", ip)
                .append("organizationUuid", organizationUuid)
                .toString();
    }

    public String getLabel() {
        return label;
    }

    public String getIp() {
        return ip;
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }
}
