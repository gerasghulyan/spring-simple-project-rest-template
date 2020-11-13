package com.vntana.core.rest.facade.token.auth;

import com.vntana.core.model.token.auth.request.TokenAuthenticationPersistRequest;
import com.vntana.core.model.token.auth.request.TokenAuthenticationPersistWithClientOrganizationRequest;
import com.vntana.core.model.token.auth.request.TokenAuthenticationPersistWithOrganizationRequest;
import com.vntana.core.model.token.auth.response.*;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 10:07 AM
 */
public interface TokenAuthenticationServiceFacade {

    PersistTokenAuthenticationResultResponse persist(final TokenAuthenticationPersistRequest request);

    PersistTokenAuthenticationResultResponse persistWithOrganization(final TokenAuthenticationPersistWithOrganizationRequest request);

    PersistTokenAuthenticationResultResponse persistWithClientOrganization(final TokenAuthenticationPersistWithClientOrganizationRequest request);

    IsExpiredTokenAuthenticationResultResponse isExpired(final String token);

    ExpireTokenAuthenticationByUserResultResponse expireByUser(final String userUuid);

    ExpireTokenAuthenticationResultResponse expire(final String token);

    FindTokenAuthenticationByTokenResponse findByToken(final String token);
}
