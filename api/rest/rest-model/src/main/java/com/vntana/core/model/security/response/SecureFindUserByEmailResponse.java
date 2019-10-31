package com.vntana.core.model.security.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.security.response.model.SecureFindUserByEmailResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;

import java.util.List;

/**
 * Created by Arthur Asatryan.
 * Date: 10/18/19
 * Time: 5:07 PM
 */
public class SecureFindUserByEmailResponse extends AbstractResultResponseModel<SecureFindUserByEmailResponseModel, UserErrorResponseModel> {
    public SecureFindUserByEmailResponse() {
    }

    public SecureFindUserByEmailResponse(final SecureFindUserByEmailResponseModel response) {
        super(response);
    }

    public SecureFindUserByEmailResponse(final List<UserErrorResponseModel> errors) {
        super(errors);
    }
}
