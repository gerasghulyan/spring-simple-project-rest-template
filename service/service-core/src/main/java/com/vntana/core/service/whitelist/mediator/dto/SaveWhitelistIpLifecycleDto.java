package com.vntana.core.service.whitelist.mediator.dto;

import com.vntana.commons.service.dto.ServiceDto;
import com.vntana.core.domain.whitelist.WhitelistType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.notNull;

/**
 * Created by Geras Ghulyan
 * Date: 18.12.19
 * Time: 16:06
 */
public class SaveWhitelistIpLifecycleDto implements ServiceDto {

    private final String organizationUuid;
    private final String organizationSlug;
    private final List<String> ips;
    private final WhitelistType type;

    public SaveWhitelistIpLifecycleDto(final String organizationUuid, final String organizationSlug, final List<String> ips, final WhitelistType type) {
        hasText(organizationUuid, "The organizationUuid should not be null or empty");
        hasText(organizationSlug, "The organizationSlug should not be null or empty");
        notNull(type, "Whitelist type cannot be null");
        this.organizationUuid = organizationUuid;
        this.organizationSlug = organizationSlug;
        this.ips = ips;
        this.type = type;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SaveWhitelistIpLifecycleDto)) {
            return false;
        }
        final SaveWhitelistIpLifecycleDto that = (SaveWhitelistIpLifecycleDto) o;
        return new EqualsBuilder()
                .append(organizationUuid, that.organizationUuid)
                .append(organizationSlug, that.organizationSlug)
                .append(ips, that.ips)
                .append(type, that.type)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(organizationUuid)
                .append(organizationSlug)
                .append(ips)
                .append(type)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("organizationUuid", organizationUuid)
                .append("organizationSlug", organizationSlug)
                .append("ips", ips)
                .append("type", type)
                .toString();
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }

    public String getOrganizationSlug() {
        return organizationSlug;
    }

    public List<String> getIps() {
        return ips;
    }

    public WhitelistType getType() {
        return type;
    }
}