package com.vntana.core.service.invitation.organization.dto;

import com.vntana.core.service.common.dto.ServiceDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Created by Geras Ghulyan
 * Date: 4/9/2020
 * Time: 10:27 AM
 */
public class AcceptInvitationOrganizationDto implements ServiceDto {

    private final String uuid;
    private final String organizationUuid;

    public AcceptInvitationOrganizationDto(final String uuid, final String organizationUuid) {
        Assert.hasText(uuid, "The uuid should not be null or empty");
        Assert.hasText(organizationUuid, "The organizationUuid should not be null or empty");
        this.uuid = uuid;
        this.organizationUuid = organizationUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof AcceptInvitationOrganizationDto)) return false;

        final AcceptInvitationOrganizationDto that = (AcceptInvitationOrganizationDto) o;

        return new EqualsBuilder()
                .append(uuid, that.uuid)
                .append(organizationUuid, that.organizationUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uuid)
                .append(organizationUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("uuid", uuid)
                .append("organizationUuid", organizationUuid)
                .toString();
    }

    public String getUuid() {
        return uuid;
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }
}