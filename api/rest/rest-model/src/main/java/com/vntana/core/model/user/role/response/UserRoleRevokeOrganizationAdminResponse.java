package com.vntana.core.model.user.role.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel;
import com.vntana.core.model.user.role.response.model.UserRoleRevokeOrganizationAdminResponseModel;

import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/12/20
 * Time: 11:40 AM
 */
public class UserRoleRevokeOrganizationAdminResponse extends AbstractResultResponseModel<UserRoleRevokeOrganizationAdminResponseModel, UserRoleErrorResponseModel> {

    public UserRoleRevokeOrganizationAdminResponse() {
        super();
    }

    public UserRoleRevokeOrganizationAdminResponse(final int httpStatusCode, final UserRoleErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public UserRoleRevokeOrganizationAdminResponse(final int httpStatusCode, final List<UserRoleErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public UserRoleRevokeOrganizationAdminResponse(final String userUuid) {
        super(new UserRoleRevokeOrganizationAdminResponseModel(userUuid));
    }
}