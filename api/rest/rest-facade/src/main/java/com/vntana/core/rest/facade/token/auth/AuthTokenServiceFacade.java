package com.vntana.core.rest.facade.token.auth;

import com.vntana.core.model.token.auth.request.AuthTokenPersistRequest;
import com.vntana.core.model.token.auth.response.AuthTokenExpireByUserResultResponse;
import com.vntana.core.model.token.auth.response.AuthTokenExpireResultResponse;
import com.vntana.core.model.token.auth.response.AuthTokenIsExpiredResultResponse;
import com.vntana.core.model.token.auth.response.AuthTokenPersistResultResponse;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 10:07 AM
 */
public interface AuthTokenServiceFacade {

    AuthTokenPersistResultResponse persist(final AuthTokenPersistRequest request);

    AuthTokenIsExpiredResultResponse isExpired(final String token);

    AuthTokenExpireByUserResultResponse expireByUser(final String userUuid);

    AuthTokenExpireResultResponse expire(final String token);
}
