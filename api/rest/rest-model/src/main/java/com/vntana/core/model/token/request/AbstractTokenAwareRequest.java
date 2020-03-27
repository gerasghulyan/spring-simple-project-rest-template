package com.vntana.core.model.token.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.token.error.TokenErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/27/20
 * Time: 11:40 AM
 */
public class AbstractTokenAwareRequest extends AbstractRequestModel implements ValidatableRequest<TokenErrorResponseModel> {

    @JsonProperty("token")
    private String token;

    public AbstractTokenAwareRequest() {
        super();
    }

    public AbstractTokenAwareRequest(final String token) {
        super();
        this.token = token;
    }

    @Override
    public List<TokenErrorResponseModel> validate() {
        final List<TokenErrorResponseModel> errors = new LinkedList<>();
        if (StringUtils.isBlank(token)) {
            errors.add(TokenErrorResponseModel.MISSING_TOKEN);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractTokenAwareRequest)) {
            return false;
        }
        final AbstractTokenAwareRequest that = (AbstractTokenAwareRequest) o;
        return new EqualsBuilder()
                .append(token, that.token)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(token)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("token", token)
                .toString();
    }

    public String getToken() {
        return token;
    }
}
