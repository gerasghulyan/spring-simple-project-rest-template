package com.vntana.core.service.whitelist.dto;

import com.vntana.commons.service.dto.ServiceDto;
import com.vntana.core.domain.whitelist.WhitelistType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Created by Arman Gevorgyan.
 * Date: 11/28/19
 * Time: 1:36 PM
 */
public class CreateWhitelistIpDto implements ServiceDto {

    private final String label;
    private final String ip;
    private final String organizationUuid;
    private final WhitelistType type;

    public CreateWhitelistIpDto(final String label, final String ip, final String organizationUuid, final WhitelistType type) {
        super();
        Assert.hasText(ip, "The whitelist ip ip should not be null or empty");
        Assert.hasText(organizationUuid, "The whitelist ip organizationUuid should not be null or empty");
        Assert.notNull(type, "The whitelist type cannot be null");
        this.label = label;
        this.ip = ip;
        this.organizationUuid = organizationUuid;
        this.type = type;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateWhitelistIpDto)) {
            return false;
        }
        final CreateWhitelistIpDto that = (CreateWhitelistIpDto) o;
        return new EqualsBuilder()
                .append(label, that.label)
                .append(ip, that.ip)
                .append(organizationUuid, that.organizationUuid)
                .append(type, type)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(label)
                .append(ip)
                .append(organizationUuid)
                .append(type)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("label", label)
                .append("ip", ip)
                .append("organizationUuid", organizationUuid)
                .append("type", type)
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

    public WhitelistType getType() {
        return type;
    }
}
