package com.vntana.core.rest.facade.token;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.model.token.error.TokenErrorResponseModel;
import com.vntana.core.model.token.request.CreateTokenInvitationOrganizationRequest;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/27/20
 * Time: 11:45 AM
 */
public interface TokeFacadePreconditionChecker {

    SingleErrorWithStatus<TokenErrorResponseModel> checkCreateTokenInvitationOrganization(final CreateTokenInvitationOrganizationRequest request);

    SingleErrorWithStatus<TokenErrorResponseModel> checkIsExpired(final String token);

    SingleErrorWithStatus<TokenErrorResponseModel> checkExpire(final String token);
}
