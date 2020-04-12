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
 * Created by Geras Ghulyan.
 * Date: 4/9/2020
 * Time: 1:45 PM
 */
public class AcceptInvitationOrganizationRequest extends AbstractRequestModel implements ValidatableRequest<InvitationOrganizationErrorResponseModel> {

    @JsonProperty("token")
    private String token;
    
    @JsonProperty("organizationName")
    private String organizationName;

    @JsonProperty("organizationSlug")
    private String organizationSlug;

    public AcceptInvitationOrganizationRequest() {
    }
 
    public AcceptInvitationOrganizationRequest(final String token, final String organizationName, final String organizationSlug) {
        this.token = token;
        this.organizationName = organizationName;
        this.organizationSlug = organizationSlug;
    }

    @Override
    public List<InvitationOrganizationErrorResponseModel> validate() {
        if (StringUtils.isBlank(token)) {
            return Collections.singletonList(InvitationOrganizationErrorResponseModel.MISSING_TOKEN);
        }
        if (StringUtils.isBlank(organizationName)) {
            return Collections.singletonList(InvitationOrganizationErrorResponseModel.MISSING_ORGANIZATION_NAME);
        }
        if (StringUtils.isBlank(organizationSlug)) {
            return Collections.singletonList(InvitationOrganizationErrorResponseModel.MISSING_ORGANIZATION_SLUG);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AcceptInvitationOrganizationRequest)) {
            return false;
        }
        final AcceptInvitationOrganizationRequest that = (AcceptInvitationOrganizationRequest) o;
        return new EqualsBuilder()
                .append(token, that.token)
                .append(organizationName, that.organizationName)
                .append(organizationSlug, that.organizationSlug)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(token)
                .append(organizationName)
                .append(organizationSlug)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("organizationName", organizationName)
                .append("organizationSlug", organizationSlug)
                .toString();
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

    public void setOrganizationName(final String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationSlug() {
        return organizationSlug;
    }

    public void setOrganizationSlug(final String organizationSlug) {
        this.organizationSlug = organizationSlug;
    }
}