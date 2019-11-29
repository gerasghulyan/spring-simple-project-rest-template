package com.vntana.core.model.whitelist.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Arman Gevorgyan.
 * Date: 11/28/19
 * Time: 5:44 PM
 */
public class CreateOrUpdateWhitelistIpItemRequestModel extends AbstractRequestModel {

    @JsonProperty("label")
    private String label;

    @JsonProperty("ip")
    private String ip;

    private CreateOrUpdateWhitelistIpItemRequestModel() {
        super();
    }

    public CreateOrUpdateWhitelistIpItemRequestModel(final String label, final String ip) {
        this.label = label;
        this.ip = ip;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateOrUpdateWhitelistIpItemRequestModel)) {
            return false;
        }
        final CreateOrUpdateWhitelistIpItemRequestModel that = (CreateOrUpdateWhitelistIpItemRequestModel) o;
        return new EqualsBuilder()
                .append(label, that.label)
                .append(ip, that.ip)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(label)
                .append(ip)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("label", label)
                .append("ip", ip)
                .toString();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(final String ip) {
        this.ip = ip;
    }
}
