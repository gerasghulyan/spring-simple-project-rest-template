package com.vntana.core.model.invitation.organization.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/27/20
 * Time: 5:16 PM
 */
public class SendInvitationOrganizationRequest extends AbstractRequestModel implements ValidatableRequest<InvitationOrganizationErrorResponseModel> {

    @JsonProperty("email")
    private String email;

    @JsonProperty("token")
    private String token;

    @JsonProperty("organizationName")
    private String organizationName;

    public SendInvitationOrganizationRequest() {
        super();
    }

    public SendInvitationOrganizationRequest(final String email, final String token, final String organizationName) {
        this.email = email;
        this.token = token;
        this.organizationName = organizationName;
    }

    @Override
    public List<InvitationOrganizationErrorResponseModel> validate() {
        final List<InvitationOrganizationErrorResponseModel> errors = new LinkedList<>();
        if (StringUtils.isBlank(getEmail())) {
            errors.add(InvitationOrganizationErrorResponseModel.MISSING_EMAIL);
        }
        if (StringUtils.isBlank(token)) {
            errors.add(InvitationOrganizationErrorResponseModel.MISSING_TOKEN);
        }
        if (StringUtils.isBlank(organizationName)) {
            errors.add(InvitationOrganizationErrorResponseModel.MISSING_ORGANIZATION_NAME);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SendInvitationOrganizationRequest)) {
            return false;
        }
        final SendInvitationOrganizationRequest that = (SendInvitationOrganizationRequest) o;
        return new EqualsBuilder()
                .append(email, that.email)
                .append(token, that.token)
                .append(organizationName, that.organizationName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(email)
                .append(token)
                .append(organizationName)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("email", email)
                .append("organizationName", organizationName)
                .toString();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public SendInvitationOrganizationRequest setOrganizationName(final String organizationName) {
        this.organizationName = organizationName;
        return this;
    }
}
