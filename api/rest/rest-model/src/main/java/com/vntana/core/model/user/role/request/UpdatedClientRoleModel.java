package com.vntana.core.model.user.role.request;

import com.vntana.core.model.auth.response.UserRoleModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Vardan Aivazian
 * Date: 17.12.2020
 * Time: 21:03
 */
public class UpdatedClientRoleModel {
    
    private final String clientUuid;
    
    private final UserRoleModel revokeUserRoleModel;
    
    private final UserRoleModel grantUserRoleModel;

    public UpdatedClientRoleModel(
            final String clientUuid,
            final UserRoleModel revokeUserRoleModel,
            final UserRoleModel grantUserRoleModel) {
        this.clientUuid = clientUuid;
        this.revokeUserRoleModel = revokeUserRoleModel;
        this.grantUserRoleModel = grantUserRoleModel;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UpdatedClientRoleModel)) {
            return false;
        }
        final UpdatedClientRoleModel that = (UpdatedClientRoleModel) o;
        return new EqualsBuilder()
                .append(clientUuid, that.clientUuid)
                .append(revokeUserRoleModel, that.revokeUserRoleModel)
                .append(grantUserRoleModel, that.grantUserRoleModel)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(clientUuid)
                .append(revokeUserRoleModel)
                .append(grantUserRoleModel)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("clientUuid", clientUuid)
                .append("revokeUserRoleModel", revokeUserRoleModel)
                .append("grantUserRoleModel", grantUserRoleModel)
                .toString();
    }

    public String getClientUuid() {
        return clientUuid;
    }

    public UserRoleModel getRevokeUserRoleModel() {
        return revokeUserRoleModel;
    }

    public UserRoleModel getGrantUserRoleModel() {
        return grantUserRoleModel;
    }
}
