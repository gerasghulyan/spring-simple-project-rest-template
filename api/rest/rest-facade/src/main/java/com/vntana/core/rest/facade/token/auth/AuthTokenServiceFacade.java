package com.vntana.core.rest.facade.token.auth;

import com.vntana.core.model.token.auth.request.AuthTokenPersistRequest;
import com.vntana.core.model.token.auth.request.AuthTokenPersistWithOrganizationRequest;
import com.vntana.core.model.token.auth.response.*;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 10:07 AM
 */
public interface AuthTokenServiceFacade {

    AuthTokenPersistResultResponse persist(final AuthTokenPersistRequest request);

    AuthTokenPersistResultResponse persistWithOrganization(final AuthTokenPersistWithOrganizationRequest request);

    AuthTokenIsExpiredResultResponse isExpired(final String token);

    AuthTokenExpireByUserResultResponse expireByUser(final String userUuid);

    AuthTokenExpireResultResponse expire(final String token);

    AuthTokenFindByTokenResponse findByToken(final String token);
}
