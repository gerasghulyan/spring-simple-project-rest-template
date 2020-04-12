package com.vntana.core.model.invitation.organization.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collections;
import java.util.List;

/**
 * Created by Manuk Gharslyan.
 * Date: 4/9/2020
 * Time: 1:45 PM
 */
public class RejectInvitationOrganizationRequest extends AbstractRequestModel implements ValidatableRequest<InvitationOrganizationErrorResponseModel> {

    @JsonProperty("token")
    private String token;

    public RejectInvitationOrganizationRequest() {
    }

    public RejectInvitationOrganizationRequest(final String token) {
        this.token = token;
    }

    @Override
    public List<InvitationOrganizationErrorResponseModel> validate() {
        if (StringUtils.isBlank(token)) {
            return Collections.singletonList(InvitationOrganizationErrorResponseModel.MISSING_TOKEN);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof RejectInvitationOrganizationRequest)) return false;

        final RejectInvitationOrganizationRequest that = (RejectInvitationOrganizationRequest) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(token, that.token)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(token)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }

    public String getToken() {
        return token;
    }
}