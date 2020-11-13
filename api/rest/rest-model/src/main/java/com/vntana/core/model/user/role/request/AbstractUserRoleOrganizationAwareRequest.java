package com.vntana.core.model.user.role.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by Manuk Gharslyan.
 * Date: 11/11/2020
 * Time: 11:05 AM
 */
public class AbstractUserRoleOrganizationAwareRequest extends AbstractUserRoleUserAwareRequest {

    @JsonProperty("organizationUuid")
    private String organizationUuid;

    public AbstractUserRoleOrganizationAwareRequest() {
        super();
    }

    public AbstractUserRoleOrganizationAwareRequest(final String userUuid, final String organizationUuid) {
        super(userUuid);
        this.organizationUuid = organizationUuid;
    }

    @Override
    public List<UserRoleErrorResponseModel> validate() {
        final List<UserRoleErrorResponseModel> errors = super.validate();
        if (StringUtils.isEmpty(organizationUuid)) {
            errors.add(UserRoleErrorResponseModel.MISSING_ORGANIZATION_UUID);
        }
        return errors;
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }

    public void setOrganizationUuid(final String organizationUuid) {
        this.organizationUuid = organizationUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof AbstractUserRoleOrganizationAwareRequest)) return false;

        final AbstractUserRoleOrganizationAwareRequest that = (AbstractUserRoleOrganizationAwareRequest) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(organizationUuid, that.organizationUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(organizationUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("organizationUuid", organizationUuid)
                .toString();
    }
}