package com.vntana.core.model.user.role.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel;
import com.vntana.core.model.user.role.response.model.UserRoleRevokeClientResponseModel;

import java.util.List;

/**
 * Created by Diana Gevorgyan
 * Date: 11/5/20
 * Time: 11:33 AM
 */
public class UserRoleRevokeClientResponse extends AbstractResultResponseModel<UserRoleRevokeClientResponseModel, UserRoleErrorResponseModel> {

    public UserRoleRevokeClientResponse() {
    }

    public UserRoleRevokeClientResponse(final int httpStatusCode, final UserRoleErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public UserRoleRevokeClientResponse(final int httpStatusCode, final List<UserRoleErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public UserRoleRevokeClientResponse(final String uuid) {
        super(new UserRoleRevokeClientResponseModel(uuid));
    }
}