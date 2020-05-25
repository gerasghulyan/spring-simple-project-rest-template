package com.vntana.core.model.token.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.core.model.token.error.TokenErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/14/20
 * Time: 2:54 PM
 */
public class CreateTokenInvitationUserRequest extends AbstractTokenAwareRequest implements ValidatableRequest<TokenErrorResponseModel> {

    @JsonProperty("invitationUserUuid")
    private String invitationUserUuid;

    public CreateTokenInvitationUserRequest() {
        super();
    }

    public CreateTokenInvitationUserRequest(final String token, final String invitationUserUuid) {
        super(token);
        this.invitationUserUuid = invitationUserUuid;
    }

    @Override
    public List<TokenErrorResponseModel> validate() {
        final List<TokenErrorResponseModel> errors = super.validate();
        if (StringUtils.isBlank(invitationUserUuid)) {
            errors.add(TokenErrorResponseModel.MISSING_INVITATION_USER_UUID);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateTokenInvitationUserRequest)) {
            return false;
        }
        final CreateTokenInvitationUserRequest that = (CreateTokenInvitationUserRequest) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(invitationUserUuid, that.invitationUserUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(invitationUserUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("invitationUserUuid", invitationUserUuid)
                .toString();
    }

    public String getInvitationUserUuid() {
        return invitationUserUuid;
    }
}
