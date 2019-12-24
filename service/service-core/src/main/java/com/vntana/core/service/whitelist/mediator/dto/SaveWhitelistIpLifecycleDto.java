package com.vntana.core.service.whitelist.mediator.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by Geras Ghulyan
 * Date: 18.12.19
 * Time: 16:06
 */
public class SaveWhitelistIpLifecycleDto {

    final String organizationUuid;
    final String organizationSlug;
    final List<String> ips;

    public SaveWhitelistIpLifecycleDto(final String organizationUuid, final String organizationSlug, final List<String> ips) {
        this.organizationUuid = organizationUuid;
        this.organizationSlug = organizationSlug;
        this.ips = ips;
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
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(organizationUuid)
                .append(organizationSlug)
                .append(ips)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("organizationUuid", organizationUuid)
                .append("organizationSlug", organizationSlug)
                .append("ips", ips)
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
}