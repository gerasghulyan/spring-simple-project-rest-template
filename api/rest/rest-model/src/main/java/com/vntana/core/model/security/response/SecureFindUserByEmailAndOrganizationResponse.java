package com.vntana.core.model.security.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.security.response.model.SecureFindUserByEmailAndOrganizationResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;

import java.util.List;

/**
 * Created by Arthur Asatryan.
 * Date: 10/18/19
 * Time: 5:07 PM
 */
public class SecureFindUserByEmailAndOrganizationResponse extends AbstractResultResponseModel<SecureFindUserByEmailAndOrganizationResponseModel, UserErrorResponseModel> {
    public SecureFindUserByEmailAndOrganizationResponse() {
    }

    public SecureFindUserByEmailAndOrganizationResponse(final SecureFindUserByEmailAndOrganizationResponseModel response) {
        super(response);
    }

    public SecureFindUserByEmailAndOrganizationResponse(final List<UserErrorResponseModel> errors) {
        super(errors);
    }
}