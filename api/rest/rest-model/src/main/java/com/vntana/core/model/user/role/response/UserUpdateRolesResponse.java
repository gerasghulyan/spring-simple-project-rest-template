package com.vntana.core.model.user.role.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel;
import com.vntana.core.model.user.role.response.model.UserUpdateRolesResponseModel;

import java.util.List;

/**
 * Created by Vardan Aivazian
 * Date: 10.12.2020
 * Time: 18:28
 */
public class UserUpdateRolesResponse extends AbstractResultResponseModel<UserUpdateRolesResponseModel, UserRoleErrorResponseModel> {

    public UserUpdateRolesResponse() {
        super();
    }

    public UserUpdateRolesResponse(final int httpStatusCode, final UserRoleErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public UserUpdateRolesResponse(final int httpStatusCode, final List<UserRoleErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public UserUpdateRolesResponse(final String userUuid) {
        super(new UserUpdateRolesResponseModel(userUuid));
    }
}
