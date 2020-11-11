package com.vntana.core.model.user.role.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by Manuk Gharslyan.
 * Date: 11/11/2020
 * Time: 11:01 AM
 */
public class AbstractUserRoleGrantClientOrganizationAwareRequest extends AbstractUserRoleGrantUserAwareRequest implements ValidatableRequest<UserRoleErrorResponseModel> {


    @JsonProperty("clientOrganizationUuid")
    private String clientOrganizationUuid;

    public AbstractUserRoleGrantClientOrganizationAwareRequest() {
        super();
    }

    public AbstractUserRoleGrantClientOrganizationAwareRequest(final String userUuid, final String clientOrganizationUuid) {
        super(userUuid);
        this.clientOrganizationUuid = clientOrganizationUuid;
    }

    @Override
    public List<UserRoleErrorResponseModel> validate() {
        final List<UserRoleErrorResponseModel> errors = super.validate();
        if (StringUtils.isEmpty(clientOrganizationUuid)) {
            errors.add(UserRoleErrorResponseModel.MISSING_CLIENT_ORGANIZATION_UUID);
        }
        return errors;
    }

    public String getClientOrganizationUuid() {
        return clientOrganizationUuid;
    }

    public void setClientOrganizationUuid(final String clientOrganizationUuid) {
        this.clientOrganizationUuid = clientOrganizationUuid;
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof AbstractUserRoleGrantClientOrganizationAwareRequest)) return false;

        final AbstractUserRoleGrantClientOrganizationAwareRequest that = (AbstractUserRoleGrantClientOrganizationAwareRequest) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(clientOrganizationUuid, that.clientOrganizationUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(clientOrganizationUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("clientOrganizationUuid", clientOrganizationUuid)
                .toString();
    }
}
