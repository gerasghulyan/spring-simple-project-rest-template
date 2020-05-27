package com.vntana.core.model.user.role.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel;
import com.vntana.core.model.user.role.response.model.UserRoleGrantSuperAdminResponseModel;

import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/27/20
 * Time: 2:22 PM
 */
public class UserRoleGrantSuperAdminResponse extends AbstractResultResponseModel<UserRoleGrantSuperAdminResponseModel, UserRoleErrorResponseModel> {

    public UserRoleGrantSuperAdminResponse() {
    }

    public UserRoleGrantSuperAdminResponse(final int httpStatusCode, final UserRoleErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public UserRoleGrantSuperAdminResponse(final int httpStatusCode, final List<UserRoleErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public UserRoleGrantSuperAdminResponse(final String userUuid) {
        super(new UserRoleGrantSuperAdminResponseModel(userUuid));
    }
}