package com.vntana.core.rest.facade.token;

import com.vntana.core.model.token.request.CreateTokenInvitationOrganizationRequest;
import com.vntana.core.model.token.request.CreateTokenInvitationUserRequest;
import com.vntana.core.model.token.response.TokenCreateResultResponse;
import com.vntana.core.model.token.response.TokenExpireResultResponse;
import com.vntana.core.model.token.response.TokenIsExpiredResultResponse;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 2:49 PM
 */
public interface TokenServiceFacade {

    TokenCreateResultResponse createTokenInvitationOrganization(final CreateTokenInvitationOrganizationRequest request);

    TokenCreateResultResponse createTokenInvitationUser(final CreateTokenInvitationUserRequest request);

    TokenIsExpiredResultResponse isExpired(final String token);

    TokenExpireResultResponse expire(final String token);
}
