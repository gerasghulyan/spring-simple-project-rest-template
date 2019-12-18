package com.vntana.core.model.user.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Arman Gevorgyan.
 * Date: 12/18/19
 * Time: 11:18 AM
 */
public class FindUserByUuidResponseModel extends AbstractUuidAwareResponseModel {

    @JsonProperty("email")
    private String email;

    public FindUserByUuidResponseModel() {
        super();
    }

    public FindUserByUuidResponseModel(final String uuid, final String email) {
        super(uuid);
        this.email = email;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FindUserByUuidResponseModel)) {
            return false;
        }
        final FindUserByUuidResponseModel that = (FindUserByUuidResponseModel) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(email, that.email)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
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
