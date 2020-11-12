package com.vntana.core.model.security.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.security.response.model.SecureUserClientOrganizationAwareResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;

/**
 * Created by Arthur Asatryan.
 * Date: 10/18/19
 * Time: 5:07 PM
 */
public class SecureFindUserByUuidAndClientOrganizationResponse extends AbstractResultResponseModel<SecureUserClientOrganizationAwareResponseModel, UserErrorResponseModel> {
    public SecureFindUserByUuidAndClientOrganizationResponse() {
        super();
    }

    public SecureFindUserByUuidAndClientOrganizationResponse(final SecureUserClientOrganizationAwareResponseModel response) {
        super(response);
    }

    public SecureFindUserByUuidAndClientOrganizationResponse(final int httpStatusCode, final UserErrorResponseModel error) {
        super(httpStatusCode, error);
    }
}