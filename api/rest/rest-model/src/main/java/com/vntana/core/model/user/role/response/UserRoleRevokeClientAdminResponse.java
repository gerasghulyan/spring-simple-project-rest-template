package com.vntana.core.model.user.role.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel;
import com.vntana.core.model.user.role.response.model.UserRoleRevokeClientAdminResponseModel;

import java.util.List;

/**
 * Created by Diana Gevorgyan
 * Date: 11/5/20
 * Time: 11:33 AM
 */
public class UserRoleRevokeClientAdminResponse extends AbstractResultResponseModel<UserRoleRevokeClientAdminResponseModel, UserRoleErrorResponseModel> {

    public UserRoleRevokeClientAdminResponse() {
    }

    public UserRoleRevokeClientAdminResponse(final int httpStatusCode, final UserRoleErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public UserRoleRevokeClientAdminResponse(final int httpStatusCode, final List<UserRoleErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public UserRoleRevokeClientAdminResponse(final String uuid) {
        super(new UserRoleRevokeClientAdminResponseModel(uuid));
    }
}
