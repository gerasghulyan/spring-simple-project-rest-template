package com.vntana.core.service.whitelist.dto;

import com.vntana.core.service.common.dto.AbstractUuidAwareDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Created by Arman Gevorgyan.
 * Date: 11/28/19
 * Time: 1:36 PM
 */
public class UpdateWhitelistIpDto extends AbstractUuidAwareDto {

    private final String label;
    private final String ip;

    public UpdateWhitelistIpDto(final String uuid, final String label, final String ip) {
        super(uuid);
        Assert.hasText(label, "The whitelist ip label should not be null or empty");
        Assert.hasText(ip, "The whitelist ip ip should not be null or empty");
        this.label = label;
        this.ip = ip;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UpdateWhitelistIpDto)) {
            return false;
        }
        final UpdateWhitelistIpDto that = (UpdateWhitelistIpDto) o;
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

    public String getIp() {
        return ip;
    }
}
