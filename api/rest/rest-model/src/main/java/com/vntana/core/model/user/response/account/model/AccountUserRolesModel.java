package com.vntana.core.model.user.response.account.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.ResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 4/7/20
 * Time: 6:30 PM
 */
public class AccountUserRolesModel implements ResponseModel {

    @JsonProperty("isSuperAdmin")
    private Boolean isSuperAdmin;

    @JsonProperty("ownerInOrganization")
    private List<String> ownerInOrganization;

    public AccountUserRolesModel() {
        super();
    }

    public AccountUserRolesModel(final Boolean isSuperAdmin, final List<String> ownerInOrganization) {
        super();
        this.isSuperAdmin = isSuperAdmin;
        this.ownerInOrganization = ownerInOrganization;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountUserRolesModel)) {
            return false;
        }
        final AccountUserRolesModel that = (AccountUserRolesModel) o;
        return new EqualsBuilder()
                .append(isSuperAdmin, that.isSuperAdmin)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(isSuperAdmin)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("isSuperAdmin", isSuperAdmin)
                .append("adminInOrganization", ownerInOrganization)
                .toString();
    }

    public Boolean getSuperAdmin() {
        return isSuperAdmin;
    }

    public AccountUserRolesModel setSuperAdmin(final Boolean superAdmin) {
        isSuperAdmin = superAdmin;
        return this;
    }

    public List<String> getOwnerInOrganization() {
        return ownerInOrganization;
    }

    public AccountUserRolesModel setOwnerInOrganization(final List<String> ownerInOrganization) {
        this.ownerInOrganization = ownerInOrganization;
        return this;
    }
}
