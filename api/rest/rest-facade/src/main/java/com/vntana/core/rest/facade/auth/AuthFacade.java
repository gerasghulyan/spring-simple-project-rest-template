package com.vntana.core.rest.facade.auth;

import com.vntana.core.model.security.request.CreatePersonalAccessTokenRequest;
import com.vntana.core.model.security.request.FindUserByPersonalAccessTokenRequest;
import com.vntana.core.model.security.request.FindUserByUuidAndClientOrganizationRequest;
import com.vntana.core.model.security.request.FindUserByUuidAndOrganizationRequest;
import com.vntana.core.model.security.response.*;
import com.vntana.core.model.user.request.FindUserByEmailRequest;
import com.vntana.core.model.user.request.RegeneratePersonalAccessTokenRequest;

/**
 * Created by Arthur Asatryan.
 * Date: 10/1/19
 * Time: 6:35 PM
 */
public interface AuthFacade {
    
    SecureFindUserByEmailResponse findByEmail(final FindUserByEmailRequest request);

    SecureFindUserByUuidAndOrganizationResponse findByUserAndOrganization(final FindUserByUuidAndOrganizationRequest request);

    SecureFindUserByUuidAndClientOrganizationResponse findByUserAndClientOrganization(final FindUserByUuidAndClientOrganizationRequest request);

    SecureFindUserByPersonalAccessTokenResponse findByPersonalAccessToken(final FindUserByPersonalAccessTokenRequest request);

    PersonalAccessTokenResponse createPersonalAccessToken(final CreatePersonalAccessTokenRequest request);

    PersonalAccessTokenExpireResponse expirePersonalAccessTokenByUserUuid(final String userUuid);

    PersonalAccessTokenResponse findPersonalAccessTokenByUserUuid(final String userUuid);

    PersonalAccessTokenResponse regeneratePersonalAccessToken(final RegeneratePersonalAccessTokenRequest request);
}
