package com.vntana.core.model.user.role.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractUuidAwareRequestModel;
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Vardan Aivazian
 * Date: 14.12.2020
 * Time: 16:36
 */
public abstract class AbstractUserUpdateRolesRequest extends AbstractUuidAwareRequestModel implements ValidatableRequest<UserRoleErrorResponseModel> {

    @JsonProperty("organizationUuid")
    private String organizationUuid;

    @JsonProperty("requestedUserUuid")
    private String requestedUserUuid;

    public AbstractUserUpdateRolesRequest() {
        super();
    }

    public AbstractUserUpdateRolesRequest(
            final String uuid,
            final String organizationUuid,
            final String requestedUserUuid) {
        super(uuid);
        this.organizationUuid = organizationUuid;
        this.requestedUserUuid = requestedUserUuid;
    }

    @Override
    public List<UserRoleErrorResponseModel> validate() {
        final List<UserRoleErrorResponseModel> errors = new LinkedList<>();
        if (StringUtils.isBlank(getUuid())) {
            errors.add(UserRoleErrorResponseModel.MISSING_USER_UUID);
        }
        if (StringUtils.isBlank(organizationUuid)) {
            errors.add(UserRoleErrorResponseModel.MISSING_ORGANIZATION_UUID);
        }
        if (StringUtils.isBlank(requestedUserUuid)) {
            errors.add(UserRoleErrorResponseModel.MISSING_REQUESTED_USER_UUID);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractUserUpdateRolesRequest)) {
            return false;
        }
        final AbstractUserUpdateRolesRequest that = (AbstractUserUpdateRolesRequest) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(organizationUuid, that.organizationUuid)
                .append(requestedUserUuid, that.requestedUserUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(organizationUuid)
                .append(requestedUserUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("user", getUuid())
                .append("organizationUuid", organizationUuid)
                .append("requestedUserUuid", requestedUserUuid)
                .toString();
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }

    public void setOrganizationUuid(final String organizationUuid) {
        this.organizationUuid = organizationUuid;
    }

    public String getRequestedUserUuid() {
        return requestedUserUuid;
    }

    public void setRequestedUserUuid(final String requestedUserUuid) {
        this.requestedUserUuid = requestedUserUuid;
    }
}
