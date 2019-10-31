package com.vntana.core.rest.facade.auth;

import com.vntana.core.model.security.request.FindUserByEmailAndOrganizationRequest;
import com.vntana.core.model.security.response.SecureFindUserByEmailAndOrganizationResponse;
import com.vntana.core.model.security.response.SecureFindUserByEmailResponse;
import com.vntana.core.model.user.request.FindUserByEmailRequest;

/**
 * Created by Arthur Asatryan.
 * Date: 10/1/19
 * Time: 6:35 PM
 */
public interface AuthServiceFacade {
    SecureFindUserByEmailResponse findByEmail(final FindUserByEmailRequest request);

    SecureFindUserByEmailAndOrganizationResponse findByEmailAndOrganization(final FindUserByEmailAndOrganizationRequest request);
}
