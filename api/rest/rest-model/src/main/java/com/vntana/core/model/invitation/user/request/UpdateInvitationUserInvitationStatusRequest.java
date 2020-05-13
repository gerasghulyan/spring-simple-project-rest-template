package com.vntana.core.model.invitation.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractUuidAwareRequestModel;
import com.vntana.core.model.invitation.InvitationStatusModel;
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collections;
import java.util.List;

import static com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel.MISSING_INVITATION_STATUS;
import static com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel.MISSING_UUID;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/13/2020
 * Time: 4:30 PM
 */
public class UpdateInvitationUserInvitationStatusRequest extends AbstractUuidAwareRequestModel implements ValidatableRequest<InvitationUserErrorResponseModel> {

    @JsonProperty("status")
    private InvitationStatusModel status;

    public UpdateInvitationUserInvitationStatusRequest() {
        super();
    }

    public UpdateInvitationUserInvitationStatusRequest(final String uuid, final InvitationStatusModel status) {
        super(uuid);
        this.status = status;
    }

    @Override
    public List<InvitationUserErrorResponseModel> validate() {
        if (StringUtils.isEmpty(getUuid())) {
            return Collections.singletonList(MISSING_UUID);
        }
        if (null == status) {
            return Collections.singletonList(MISSING_INVITATION_STATUS);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof UpdateInvitationUserInvitationStatusRequest)) return false;

        final UpdateInvitationUserInvitationStatusRequest that = (UpdateInvitationUserInvitationStatusRequest) o;

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

    public InvitationStatusModel getStatus() {
        return status;
    }

    public void setStatus(final InvitationStatusModel status) {
        this.status = status;
    }
}