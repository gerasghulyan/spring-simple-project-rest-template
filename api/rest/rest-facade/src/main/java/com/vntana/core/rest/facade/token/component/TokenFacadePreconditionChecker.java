package com.vntana.core.rest.facade.token.component;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.model.token.error.TokenErrorResponseModel;
import com.vntana.core.model.token.request.CreateTokenInvitationOrganizationRequest;
import com.vntana.core.model.token.request.CreateTokenInvitationUserRequest;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/27/20
 * Time: 11:45 AM
 */
public interface TokenFacadePreconditionChecker {

    SingleErrorWithStatus<TokenErrorResponseModel> checkCreateTokenInvitationOrganization(final CreateTokenInvitationOrganizationRequest request);

    SingleErrorWithStatus<TokenErrorResponseModel> checkCreateTokenInvitationUser(final CreateTokenInvitationUserRequest request);

    SingleErrorWithStatus<TokenErrorResponseModel> checkIsExpired(final String token);

    SingleErrorWithStatus<TokenErrorResponseModel> checkExpire(final String token);
}
