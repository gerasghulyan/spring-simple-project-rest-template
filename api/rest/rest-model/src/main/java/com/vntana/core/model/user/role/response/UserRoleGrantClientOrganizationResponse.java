package com.vntana.core.model.user.role.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel;
import com.vntana.core.model.user.role.response.model.UserRoleGrantClientOrganzationResponseModel;

import java.util.List;

/**
 * Created by Diana Gevorgyan
 * Date: 11/5/20
 * Time: 9:54 AM
 */
public class UserRoleGrantClientOrganizationResponse extends AbstractResultResponseModel<UserRoleGrantClientOrganzationResponseModel, UserRoleErrorResponseModel> {

    public UserRoleGrantClientOrganizationResponse() {
    }

    public UserRoleGrantClientOrganizationResponse(final int httpStatusCode, final UserRoleErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public UserRoleGrantClientOrganizationResponse(final int httpStatusCode, final List<UserRoleErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public UserRoleGrantClientOrganizationResponse(final String userUuid) {
        super(new UserRoleGrantClientOrganzationResponseModel(userUuid));
    }
}
