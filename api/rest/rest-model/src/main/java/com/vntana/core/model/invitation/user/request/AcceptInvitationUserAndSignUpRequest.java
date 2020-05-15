package com.vntana.core.model.invitation.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collections;
import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/15/20
 * Time: 4:03 PM
 */
public class AcceptInvitationUserAndSignUpRequest extends AbstractRequestModel implements ValidatableRequest<InvitationUserErrorResponseModel> {

    @JsonProperty("token")
    private String token;

    @JsonProperty("newUserFullName")
    private String newUserFullName;

    @JsonProperty("password")
    private String password;

    public AcceptInvitationUserAndSignUpRequest() {
        super();
    }

    public AcceptInvitationUserAndSignUpRequest(final String token, final String newUserFullName, final String password) {
        this.token = token;
        this.newUserFullName = newUserFullName;
        this.password = password;
    }

    @Override
    public List<InvitationUserErrorResponseModel> validate() {
        if (StringUtils.isEmpty(token)) {
            return Collections.singletonList(InvitationUserErrorResponseModel.MISSING_TOKEN);
        }
        if (StringUtils.isEmpty(newUserFullName)) {
            return Collections.singletonList(InvitationUserErrorResponseModel.MISSING_USER_FULL_NAME);
        }
        if (StringUtils.isEmpty(password)) {
            return Collections.singletonList(InvitationUserErrorResponseModel.MISSING_PASSWORD);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AcceptInvitationUserAndSignUpRequest)) {
            return false;
        }
        final AcceptInvitationUserAndSignUpRequest that = (AcceptInvitationUserAndSignUpRequest) o;
        return new EqualsBuilder()
                .append(token, that.token)
                .append(newUserFullName, that.newUserFullName)
                .append(password, that.password)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(token)
                .append(newUserFullName)
                .append(password)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("token", token)
                .append("newUserFullName", newUserFullName)
                .append("password", "***")
                .toString();
    }

    public String getToken() {
        return token;
    }

    public String getNewUserFullName() {
        return newUserFullName;
    }

    public String getPassword() {
        return password;
    }
}
