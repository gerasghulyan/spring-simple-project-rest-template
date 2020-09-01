package com.vntana.core.service.invitation.user.dto;

import com.vntana.commons.service.dto.ServiceDto;
import com.vntana.core.domain.invitation.InvitationStatus;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 12:32 PM
 */
public class UpdateInvitationUserStatusDto implements ServiceDto {

    private final String uuid;
    private final InvitationStatus status;

    public UpdateInvitationUserStatusDto(final String uuid, final InvitationStatus status) {
        Assert.hasText(uuid, "The uuid should not be null or empty");
        Assert.notNull(status, "The status should not be null");
        this.uuid = uuid;
        this.status = status;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof UpdateInvitationUserStatusDto)) return false;

        final UpdateInvitationUserStatusDto that = (UpdateInvitationUserStatusDto) o;

        return new EqualsBuilder()
                .append(uuid, that.uuid)
                .append(status, that.status)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uuid)
                .append(status)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("uuid", uuid)
                .append("status", status)
                .toString();
    }

    public String getUuid() {
        return uuid;
    }

    public InvitationStatus getStatus() {
        return status;
    }
}