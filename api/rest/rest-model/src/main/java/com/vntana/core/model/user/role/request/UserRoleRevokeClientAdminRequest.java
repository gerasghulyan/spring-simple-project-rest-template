package com.vntana.core.model.user.role.request;

import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by Diana Gevorgyan
 * Date: 11/5/20
 * Time: 11:38 AM
 */
public class UserRoleRevokeClientAdminRequest extends AbstractRequestModel implements ValidatableRequest<UserRoleErrorResponseModel> {

    private String userUuid;

    private String clientOrganizationUuid;

    public UserRoleRevokeClientAdminRequest() {
        super();
    }

    public UserRoleRevokeClientAdminRequest(final String userUuid, final String clientOrganizationUuid) {
        this.userUuid = userUuid;
        this.clientOrganizationUuid = clientOrganizationUuid;
    }

    @Override
    public List<UserRoleErrorResponseModel> validate() {
        final List<UserRoleErrorResponseModel> errors = initializeNew();
        if (StringUtils.isEmpty(clientOrganizationUuid)) {
            errors.add(UserRoleErrorResponseModel.MISSING_CLIENT_ORGANIZATION_UUID);
        }
        if (StringUtils.isEmpty(userUuid)) {
            errors.add(UserRoleErrorResponseModel.MISSING_USER_UUID);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserRoleRevokeClientAdminRequest)) {
            return false;
        }
        final UserRoleRevokeClientAdminRequest that = (UserRoleRevokeClientAdminRequest) o;
        return new EqualsBuilder()
                .append(userUuid, that.userUuid)
                .append(clientOrganizationUuid, that.clientOrganizationUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(userUuid)
                .append(clientOrganizationUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("userUuid", userUuid)
                .append("clientOrganizationUuid", clientOrganizationUuid)
                .toString();
    }

    public String getUserUuid() {
        return userUuid;
    }

    public String getClientOrganizationUuid() {
        return clientOrganizationUuid;
    }
}
