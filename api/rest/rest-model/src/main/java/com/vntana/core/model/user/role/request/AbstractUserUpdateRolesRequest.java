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
 * Created by Vardan Aivazian
 * Date: 14.12.2020
 * Time: 16:36
 */
public abstract class AbstractUserUpdateRolesRequest extends AbstractRequestModel implements ValidatableRequest<UserRoleErrorResponseModel> {

    @JsonProperty("authorizedUserUuid")
    private String authorizedUserUuid;

    @JsonProperty("organizationUuid")
    private String organizationUuid;

    @JsonProperty("userUuid")
    private String userUuid;

    public AbstractUserUpdateRolesRequest() {
        super();
    }

    public AbstractUserUpdateRolesRequest(
            final String authorizedUserUuid,
            final String organizationUuid,
            final String userUuid) {
        this.authorizedUserUuid = authorizedUserUuid;
        this.organizationUuid = organizationUuid;
        this.userUuid = userUuid;
    }

    @Override
    public List<UserRoleErrorResponseModel> validate() {
        if (StringUtils.isBlank(authorizedUserUuid)) {
            return Collections.singletonList(UserRoleErrorResponseModel.MISSING_AUTHORIZED_USER_UUID);
        }
        if (StringUtils.isBlank(organizationUuid)) {
            return Collections.singletonList(UserRoleErrorResponseModel.MISSING_ORGANIZATION_UUID);
        }
        if (StringUtils.isBlank(userUuid)) {
            return Collections.singletonList(UserRoleErrorResponseModel.MISSING_USER_UUID);
        }
        return Collections.emptyList();
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
                .append(authorizedUserUuid, that.authorizedUserUuid)
                .append(organizationUuid, that.organizationUuid)
                .append(userUuid, that.userUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(authorizedUserUuid)
                .append(organizationUuid)
                .append(userUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("authorizedUserUuid", authorizedUserUuid)
                .append("organizationUuid", organizationUuid)
                .append("userUuid", userUuid)
                .toString();
    }

    public String getAuthorizedUserUuid() {
        return authorizedUserUuid;
    }

    public void setAuthorizedUserUuid(final String authorizedUserUuid) {
        this.authorizedUserUuid = authorizedUserUuid;
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }

    public void setOrganizationUuid(final String organizationUuid) {
        this.organizationUuid = organizationUuid;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(final String userUuid) {
        this.userUuid = userUuid;
    }
}
