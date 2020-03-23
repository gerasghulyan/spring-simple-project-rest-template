package com.vntana.core.rest.facade.token;

import com.vntana.core.model.token.response.TokenExpireResultResponse;
import com.vntana.core.model.token.response.TokenIsExpiredResultResponse;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 2:49 PM
 */
public interface TokenServiceFacade {

    TokenIsExpiredResultResponse isExpired(final String token);

    TokenExpireResultResponse expire(final String token);
}
