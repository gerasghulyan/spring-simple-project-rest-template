package com.vntana.core.model.user.role.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.core.model.auth.response.UserRoleModel;
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;
import java.util.Objects;

/**
 * Created by Manuk Gharslyan.
 * Date: 11/11/2020
 * Time: 11:01 AM
 */
public class AbstractUserRoleClientOrganizationAwareRequest extends AbstractUserRoleUserAwareRequest implements ValidatableRequest<UserRoleErrorResponseModel> {

    @JsonProperty("clientUuid")
    private String clientUuid;

    @JsonProperty("userRole")
    private UserRoleModel userRole;

    public AbstractUserRoleClientOrganizationAwareRequest() {
        super();
    }

    public AbstractUserRoleClientOrganizationAwareRequest(final String userUuid, final String clientUuid, final UserRoleModel userRole) {
        super(userUuid);
        this.clientUuid = clientUuid;
        this.userRole = userRole;
    }

    @Override
    public List<UserRoleErrorResponseModel> validate() {
        final List<UserRoleErrorResponseModel> errors = super.validate();
        if (StringUtils.isEmpty(clientUuid)) {
            errors.add(UserRoleErrorResponseModel.MISSING_CLIENT_ORGANIZATION_UUID);
        }
        if (Objects.isNull(userRole)) {
            errors.add(UserRoleErrorResponseModel.MISSING_CLIENT_ROLE);
        }
        if (!userRole.isClientRelatedRole()) {
            errors.add(UserRoleErrorResponseModel.REQUEST_ROLE_IS_NOT_CLIENT_RELATED);
        }
        return errors;
    }

    public String getClientUuid() {
        return clientUuid;
    }

    public void setClientUuid(final String clientUuid) {
        this.clientUuid = clientUuid;
    }

    public void setUserRole(final UserRoleModel userRole) {
        this.userRole = userRole;
    }

    public UserRoleModel getUserRole() {
        return userRole;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof AbstractUserRoleClientOrganizationAwareRequest)) return false;

        final AbstractUserRoleClientOrganizationAwareRequest that = (AbstractUserRoleClientOrganizationAwareRequest) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(clientUuid, that.clientUuid)
                .append(userRole, that.userRole)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(clientUuid)
                .append(userRole)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("clientUuid", clientUuid)
                .append("userRole", userRole)
                .toString();
    }
}
