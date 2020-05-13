package com.vntana.core.service.invitation.user.dto;

import com.vntana.core.domain.invitation.InvitationStatus;
import com.vntana.core.service.common.dto.AbstractPaginationAwareDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 12:32 PM
 */
public class GetAllByStatusInvitationUsersDto extends AbstractPaginationAwareDto {
    private final InvitationStatus status;

    public GetAllByStatusInvitationUsersDto(final int size, final InvitationStatus status) {
        super(size);
        Assert.notNull(status, "The invitation status could not be null");
        this.status = status;
    }

    public GetAllByStatusInvitationUsersDto(final int page, final int size, final InvitationStatus status) {
        super(page, size);
        Assert.notNull(status, "The invitation status could not be null");
        this.status = status;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof GetAllByStatusInvitationUsersDto)) return false;

        final GetAllByStatusInvitationUsersDto that = (GetAllByStatusInvitationUsersDto) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(status, that.status)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(status)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("status", status)
                .toString();
    }

    public InvitationStatus getStatus() {
        return status;
    }
}