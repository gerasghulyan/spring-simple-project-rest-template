package com.vntana.core.rest.facade.auth;

import com.vntana.core.model.security.request.FindUserByUuidAndClientOrganizationRequest;
import com.vntana.core.model.security.request.FindUserByUuidAndOrganizationRequest;
import com.vntana.core.model.security.response.SecureFindUserByEmailResponse;
import com.vntana.core.model.security.response.SecureFindUserByUuidAndClientOrganizationResponse;
import com.vntana.core.model.security.response.SecureFindUserByUuidAndOrganizationResponse;
import com.vntana.core.model.user.request.FindUserByEmailRequest;

/**
 * Created by Arthur Asatryan.
 * Date: 10/1/19
 * Time: 6:35 PM
 */
public interface AuthFacade {
    SecureFindUserByEmailResponse findByEmail(final FindUserByEmailRequest request);

    SecureFindUserByUuidAndOrganizationResponse findByUserAndOrganization(final FindUserByUuidAndOrganizationRequest request);

    SecureFindUserByUuidAndClientOrganizationResponse findByUserAndClientOrganization(final FindUserByUuidAndClientOrganizationRequest request);
}
