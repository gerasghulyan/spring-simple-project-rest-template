package com.vntana.core.model.invitation.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractPaginationAwareRequestModel;
import com.vntana.core.model.invitation.InvitationStatusModel;
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collections;
import java.util.List;

import static com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel.*;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/13/2020
 * Time: 3:30 PM
 */
public class GetAllByStatusInvitationUserRequest extends AbstractPaginationAwareRequestModel implements ValidatableRequest<InvitationUserErrorResponseModel> {

    @JsonProperty("organizationUuid")
    private String organizationUuid;

    @JsonProperty("status")
    private InvitationStatusModel status;

    public GetAllByStatusInvitationUserRequest() {
        super();
    }

    public GetAllByStatusInvitationUserRequest(final Integer page, final Integer size, final String organizationUuid, final InvitationStatusModel status) {
        super(page, size);
        this.organizationUuid = organizationUuid;
        this.status = status;
    }

    @Override
    public List<InvitationUserErrorResponseModel> validate() {
        if (StringUtils.isEmpty(organizationUuid)) {
            return Collections.singletonList(MISSING_INVITING_ORGANIZATION_UUID);
        }
        if (null == status) {
            return Collections.singletonList(MISSING_INVITATION_STATUS);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof GetAllByStatusInvitationUserRequest)) return false;

        final GetAllByStatusInvitationUserRequest that = (GetAllByStatusInvitationUserRequest) o;

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

    public InvitationStatusModel getStatus() {
        return status;
    }

    public void setStatus(final InvitationStatusModel status) {
        this.status = status;
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }
}