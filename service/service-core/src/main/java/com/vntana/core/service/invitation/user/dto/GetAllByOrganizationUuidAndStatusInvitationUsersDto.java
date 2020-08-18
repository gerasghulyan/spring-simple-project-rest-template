package com.vntana.core.service.invitation.user.dto;

import com.vntana.core.domain.invitation.InvitationStatus;
import com.vntana.commons.service.dto.AbstractPaginationAwareDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 12:32 PM
 */
public class GetAllByOrganizationUuidAndStatusInvitationUsersDto extends AbstractPaginationAwareDto {
    private final String organizationUuid;
    private final InvitationStatus status;

    public GetAllByOrganizationUuidAndStatusInvitationUsersDto(final int size, final String organizationUuid, final InvitationStatus status) {
        super(size);
        Assert.notNull(status, "The invitation status should not be null");
        Assert.hasText(organizationUuid, "The organizationUuid status should not be null or empty");
        this.organizationUuid = organizationUuid;
        this.status = status;
    }

    public GetAllByOrganizationUuidAndStatusInvitationUsersDto(final int page, final int size, final String organizationUuid, final InvitationStatus status) {
        super(page, size);
        Assert.notNull(status, "The invitation status could not be null");
        Assert.hasText(organizationUuid, "The organizationUuid status should not be null or empty");
        this.organizationUuid = organizationUuid;
        this.status = status;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof GetAllByOrganizationUuidAndStatusInvitationUsersDto)) return false;

        final GetAllByOrganizationUuidAndStatusInvitationUsersDto that = (GetAllByOrganizationUuidAndStatusInvitationUsersDto) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(organizationUuid, that.organizationUuid)
                .append(status, that.status)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(organizationUuid)
                .append(status)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("organizationUuid", organizationUuid)
                .append("status", status)
                .toString();
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }

    public InvitationStatus getStatus() {
        return status;
    }
}