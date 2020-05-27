package com.vntana.core.model.user.role.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collections;
import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/27/20
 * Time: 2:24 PM
 */
public class UserRoleGrantSuperAdminRequest extends AbstractRequestModel implements ValidatableRequest<UserRoleErrorResponseModel> {

    @JsonProperty("userUuid")
    private String userUuid;

    public UserRoleGrantSuperAdminRequest() {
        super();
    }

    public UserRoleGrantSuperAdminRequest(final String userUuid) {
        this.userUuid = userUuid;
    }

    @Override
    public List<UserRoleErrorResponseModel> validate() {
        if (StringUtils.isEmpty(userUuid)) {
            return Collections.singletonList(UserRoleErrorResponseModel.MISSING_USER_UUID);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserRoleGrantSuperAdminRequest)) {
            return false;
        }
        final UserRoleGrantSuperAdminRequest that = (UserRoleGrantSuperAdminRequest) o;
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

    public String getUserUuid() {
        return userUuid;
    }
}
