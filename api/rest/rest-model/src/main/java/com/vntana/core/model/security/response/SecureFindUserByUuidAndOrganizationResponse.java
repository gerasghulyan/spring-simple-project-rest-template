package com.vntana.core.model.security.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.security.response.model.SecureUserOrganizationResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;

import java.util.List;

/**
 * Created by Arthur Asatryan.
 * Date: 10/18/19
 * Time: 5:07 PM
 */
public class SecureFindUserByUuidAndOrganizationResponse extends AbstractResultResponseModel<SecureUserOrganizationResponseModel, UserErrorResponseModel> {
    public SecureFindUserByUuidAndOrganizationResponse() {
    }

    public SecureFindUserByUuidAndOrganizationResponse(final SecureUserOrganizationResponseModel response) {
        super(response);
    }

    public SecureFindUserByUuidAndOrganizationResponse(final List<UserErrorResponseModel> errors) {
        super(errors);
    }
}
