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
 * Date: 3/27/20
 * Time: 11:37 AM
 */
public class CreateTokenInvitationOrganizationRequest extends AbstractTokenAwareRequest implements ValidatableRequest<TokenErrorResponseModel> {

    @JsonProperty("invitationOrganizationUuid")
    private String invitationOrganizationUuid;

    public CreateTokenInvitationOrganizationRequest() {
        super();
    }

    public CreateTokenInvitationOrganizationRequest(final String token, final String invitationOrganizationUuid) {
        super(token);
        this.invitationOrganizationUuid = invitationOrganizationUuid;
    }

    @Override
    public List<TokenErrorResponseModel> validate() {
        final List<TokenErrorResponseModel> errors = super.validate();
        if (StringUtils.isBlank(invitationOrganizationUuid)) {
            errors.add(TokenErrorResponseModel.MISSING_INVITATION_ORGANIZATION_UUID);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateTokenInvitationOrganizationRequest)) {
            return false;
        }
        final CreateTokenInvitationOrganizationRequest that = (CreateTokenInvitationOrganizationRequest) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(invitationOrganizationUuid, that.invitationOrganizationUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(invitationOrganizationUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("invitationOrganizationUuid", invitationOrganizationUuid)
                .toString();
    }

    public String getInvitationOrganizationUuid() {
        return invitationOrganizationUuid;
    }
}
