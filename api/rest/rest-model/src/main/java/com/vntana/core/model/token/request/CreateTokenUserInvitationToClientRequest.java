package com.vntana.core.model.token.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.token.error.TokenErrorResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by Diana Gevorgyan
 * Date: 11/19/20
 * Time: 12:10 PM
 */
public class CreateTokenUserInvitationToClientRequest extends AbstractRequestModel implements ValidatableRequest<TokenErrorResponseModel> {
    
    @JsonProperty("tokens")
    private List<InvitationAndTokenRequestModel> tokens;

    public CreateTokenUserInvitationToClientRequest() {
    }

    public CreateTokenUserInvitationToClientRequest(final List<InvitationAndTokenRequestModel> tokens) {
        this.tokens = tokens;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateTokenUserInvitationToClientRequest)) {
            return false;
        }
        final CreateTokenUserInvitationToClientRequest that = (CreateTokenUserInvitationToClientRequest) o;
        return new EqualsBuilder()
                .append(tokens, that.tokens)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(tokens)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("tokens", tokens)
                .toString();
    }

    public List<InvitationAndTokenRequestModel> getTokens() {
        return tokens;
    }
}
