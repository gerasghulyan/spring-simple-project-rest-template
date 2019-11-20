package com.vntana.core.model.user.request;

import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Arman Gevorgyan.
 * Date: 11/11/19
 * Time: 1:58 PM
 */
public class VerifyUserRequest extends AbstractRequestModel {

    private String email;

    public VerifyUserRequest() {
    }

    public VerifyUserRequest(final String email) {
        this.email = email;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VerifyUserRequest)) {
            return false;
        }
        final VerifyUserRequest that = (VerifyUserRequest) o;
        return new EqualsBuilder()
                .append(email, that.email)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(email)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("email", email)
                .toString();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }
}
