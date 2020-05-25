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
 * Created by Arman Gevorgyan.
 * Date: 5/12/20
 * Time: 4:24 PM
 */
public class UserRoleGrantOrganizationAdminRequest extends AbstractRequestModel implements ValidatableRequest<UserRoleErrorResponseModel> {

    private String organizationUuid;

    private String userUuid;

    public UserRoleGrantOrganizationAdminRequest() {
        super();
    }

    public UserRoleGrantOrganizationAdminRequest(final String organizationUuid, final String userUuid) {
        this.organizationUuid = organizationUuid;
        this.userUuid = userUuid;
    }

    @Override
    public List<UserRoleErrorResponseModel> validate() {
        final List<UserRoleErrorResponseModel> errors = initializeNew();
        if (StringUtils.isEmpty(organizationUuid)) {
            errors.add(UserRoleErrorResponseModel.MISSING_ORGANIZATION_UUID);
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
        if (!(o instanceof UserRoleGrantOrganizationAdminRequest)) {
            return false;
        }
        final UserRoleGrantOrganizationAdminRequest that = (UserRoleGrantOrganizationAdminRequest) o;
        return new EqualsBuilder()
                .append(organizationUuid, that.organizationUuid)
                .append(userUuid, that.userUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(organizationUuid)
                .append(userUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("organizationUuid", organizationUuid)
                .append("userUuid", userUuid)
                .toString();
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }

    public String getUserUuid() {
        return userUuid;
    }
}
