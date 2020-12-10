package com.vntana.core.model.user.role.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

import static com.vntana.core.model.user.role.error.UserRoleErrorResponseModel.MISSING_REVOCABLE_USER_UUID;

/**
 * Created by Diana Gevorgyan
 * Date: 11/5/20
 * Time: 11:38 AM
 */
public class UserRoleRevokeOrganizationClientsRequest extends AbstractUserRoleOrganizationAwareRequest {

    @JsonProperty("revocableUserUuid")
    private String revocableUserUuid;

    public UserRoleRevokeOrganizationClientsRequest() {
        super();
    }

    public UserRoleRevokeOrganizationClientsRequest(final String userUuid, final String organizationUuid, final String revocableUserUuid) {
        super(userUuid, organizationUuid);
        this.revocableUserUuid = revocableUserUuid;
    }

    @Override
    public List<UserRoleErrorResponseModel> validate() {
        final List<UserRoleErrorResponseModel> errors = super.validate();
        if (StringUtils.isEmpty(revocableUserUuid)) {
            errors.add(MISSING_REVOCABLE_USER_UUID);
        }
        return errors;
    }

    public String getRevocableUserUuid() {
        return revocableUserUuid;
    }

    public void setRevocableUserUuid(final String revocableUserUuid) {
        this.revocableUserUuid = revocableUserUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof UserRoleRevokeOrganizationClientsRequest)) return false;

        final UserRoleRevokeOrganizationClientsRequest that = (UserRoleRevokeOrganizationClientsRequest) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(revocableUserUuid, that.revocableUserUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(revocableUserUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("revocableUserUuid", revocableUserUuid)
                .toString();
    }
}