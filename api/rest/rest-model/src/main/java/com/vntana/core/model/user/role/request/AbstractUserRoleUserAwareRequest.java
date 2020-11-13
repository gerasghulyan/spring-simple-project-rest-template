package com.vntana.core.model.user.role.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Manuk Gharslyan.
 * Date: 11/11/2020
 * Time: 11:09 AM
 */
public class AbstractUserRoleUserAwareRequest extends AbstractRequestModel implements ValidatableRequest<UserRoleErrorResponseModel> {

    @JsonProperty("userUuid")
    private String userUuid;

    public AbstractUserRoleUserAwareRequest() {
        super();
    }

    public AbstractUserRoleUserAwareRequest(final String userUuid) {
        this.userUuid = userUuid;
    }

    @Override
    public List<UserRoleErrorResponseModel> validate() {
        final List<UserRoleErrorResponseModel> errors = new LinkedList<>();
        if (StringUtils.isEmpty(userUuid)) {
            errors.add(UserRoleErrorResponseModel.MISSING_USER_UUID);
        }
        return errors;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(final String userUuid) {
        this.userUuid = userUuid;
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof AbstractUserRoleUserAwareRequest)) return false;

        final AbstractUserRoleUserAwareRequest that = (AbstractUserRoleUserAwareRequest) o;

        return new EqualsBuilder()
                .append(userUuid, that.userUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(userUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("userUuid", userUuid)
                .toString();
    }
}