package com.vntana.core.model.invitation.organization.request;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class AcceptAndSignUpInvitationOrganizationRequest extends AcceptInvitationOrganizationRequest {

    @JsonProperty("userFullName")
    private String userFullName;

    @JsonProperty("userPassword")
    private String userPassword;

    public AcceptAndSignUpInvitationOrganizationRequest() {
    }

    public AcceptAndSignUpInvitationOrganizationRequest(final String token, final String organizationName, final String organizationSlug, final String userFullName, final String userPassword) {
        super(token, organizationName, organizationSlug);
        this.userFullName = userFullName;
        this.userPassword = userPassword;
    }

    @Override
    public List<InvitationOrganizationErrorResponseModel> validate() {
        if (StringUtils.isBlank(getToken())) {
            return Collections.singletonList(InvitationOrganizationErrorResponseModel.MISSING_TOKEN);
        }
        if (StringUtils.isBlank(getOrganizationName())) {
            return Collections.singletonList(InvitationOrganizationErrorResponseModel.MISSING_ORGANIZATION_NAME);
        }
        if (StringUtils.isBlank(getOrganizationSlug())) {
            return Collections.singletonList(InvitationOrganizationErrorResponseModel.MISSING_ORGANIZATION_SLUG);
        }
        if (StringUtils.isBlank(userFullName)) {
            return Collections.singletonList(InvitationOrganizationErrorResponseModel.MISSING_USER_FULL_NAME);
        }
        if (StringUtils.isBlank(userPassword)) {
            return Collections.singletonList(InvitationOrganizationErrorResponseModel.MISSING_USER_PASSWORD);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AcceptAndSignUpInvitationOrganizationRequest)) {
            return false;
        }
        final AcceptAndSignUpInvitationOrganizationRequest that = (AcceptAndSignUpInvitationOrganizationRequest) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(userFullName, that.userFullName)
                .append(userPassword, that.userPassword)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(userFullName)
                .append(userPassword)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("userFullName", userFullName)
                .append("userPassword", userPassword)
                .toString();
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(final String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(final String userPassword) {
        this.userPassword = userPassword;
    }
}