package com.vntana.core.model.user.role.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel;
import com.vntana.core.model.user.role.response.model.UserRoleGrantOrganizationAdminResponseModel;

import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/12/20
 * Time: 4:24 PM
 */
public class UserRoleGrantOrganizationAdminResponse extends AbstractResultResponseModel<UserRoleGrantOrganizationAdminResponseModel, UserRoleErrorResponseModel> {

    public UserRoleGrantOrganizationAdminResponse() {
    }

    public UserRoleGrantOrganizationAdminResponse(final int httpStatusCode, final UserRoleErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public UserRoleGrantOrganizationAdminResponse(final int httpStatusCode, final List<UserRoleErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public UserRoleGrantOrganizationAdminResponse(final String userUuid) {
        super(new UserRoleGrantOrganizationAdminResponseModel(userUuid));
    }
}