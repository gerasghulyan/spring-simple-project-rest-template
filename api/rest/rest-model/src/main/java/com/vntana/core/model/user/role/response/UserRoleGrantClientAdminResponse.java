package com.vntana.core.model.user.role.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel;
import com.vntana.core.model.user.role.response.model.UserRoleGrantClientAdminResponseModel;

import java.util.List;

/**
 * Created by Diana Gevorgyan
 * Date: 11/5/20
 * Time: 9:54 AM
 */
public class UserRoleGrantClientAdminResponse extends AbstractResultResponseModel<UserRoleGrantClientAdminResponseModel, UserRoleErrorResponseModel> {

    public UserRoleGrantClientAdminResponse() {
    }

    public UserRoleGrantClientAdminResponse(final int httpStatusCode, final UserRoleErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public UserRoleGrantClientAdminResponse(final int httpStatusCode, final List<UserRoleErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public UserRoleGrantClientAdminResponse(final String userUuid) {
        super(new UserRoleGrantClientAdminResponseModel(userUuid));
    }
}
