package com.vntana.core.model.invitation.organization.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractUuidAwareRequestModel;
import com.vntana.core.model.invitation.InvitationStatusModel;
import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collections;
import java.util.List;

/**
 * Created by Manuk Gharslyan.
 * Date: 4/9/2020
 * Time: 10:58 AM
 */
public class UpdateInvitationOrganizationStatusRequest extends AbstractUuidAwareRequestModel implements ValidatableRequest<InvitationOrganizationErrorResponseModel> {

    @JsonProperty("status")
    private InvitationStatusModel status;

    public UpdateInvitationOrganizationStatusRequest() {
    }

    public UpdateInvitationOrganizationStatusRequest(final String uuid, final InvitationStatusModel status) {
        super(uuid);
        this.status = status;
    }

    @Override
    public List<InvitationOrganizationErrorResponseModel> validate() {
        if (status == null) {
            return Collections.singletonList(InvitationOrganizationErrorResponseModel.MISSING_INVITATION_STATUS);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof UpdateInvitationOrganizationStatusRequest)) return false;

        final UpdateInvitationOrganizationStatusRequest that = (UpdateInvitationOrganizationStatusRequest) o;

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