package com.vntana.core.service.invitation.user.dto;

import com.vntana.core.domain.invitation.InvitationStatus;
import com.vntana.core.service.common.dto.ServiceDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/12/2020
 * Time: 2:57 PM
 */
public class GetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto implements ServiceDto {

    private final String email;
    private final String organizationUuid;
    private final InvitationStatus status;

    public GetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto(final String email, final String organizationUuid, final InvitationStatus status) {
        Assert.hasText(email, "The email should not be null or empty");
        Assert.hasText(organizationUuid, "The organizationUuid should not be null or empty");
        Assert.notNull(status, "The status should not be null");
        this.email = email;
        this.organizationUuid = organizationUuid;
        this.status = status;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof GetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto)) return false;

        final GetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto that = (GetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto) o;

        return new EqualsBuilder()
                .append(email, that.email)
                .append(organizationUuid, that.organizationUuid)
                .append(status, that.status)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(email)
                .append(organizationUuid)
                .append(status)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("email", email)
                .append("organizationUuid", organizationUuid)
                .append("status", status)
                .toString();
    }

    public String getEmail() {
        return email;
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }

    public InvitationStatus getStatus() {
        return status;
    }
}