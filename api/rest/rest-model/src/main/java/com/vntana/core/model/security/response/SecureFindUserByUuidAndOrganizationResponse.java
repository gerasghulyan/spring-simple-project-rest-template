package com.vntana.core.model.security.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.security.response.model.SecureUserOrganizationAwareResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;

/**
 * Created by Arthur Asatryan.
 * Date: 10/18/19
 * Time: 5:07 PM
 */
public class SecureFindUserByUuidAndOrganizationResponse extends AbstractResultResponseModel<SecureUserOrganizationAwareResponseModel, UserErrorResponseModel> {
    public SecureFindUserByUuidAndOrganizationResponse() {
    }

    public SecureFindUserByUuidAndOrganizationResponse(final SecureUserOrganizationAwareResponseModel response) {
        super(response);
    }

    public SecureFindUserByUuidAndOrganizationResponse(final int httpStatusCode, final UserErrorResponseModel error) {
        super(httpStatusCode, error);
    }
}
