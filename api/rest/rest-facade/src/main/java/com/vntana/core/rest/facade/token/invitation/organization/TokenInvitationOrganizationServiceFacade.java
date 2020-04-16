package com.vntana.core.rest.facade.token.invitation.organization;

import com.vntana.core.model.token.request.CreateTokenInvitationOrganizationRequest;
import com.vntana.core.model.token.response.TokenCreateResultResponse;

/**
 * Created by Geras Ghulyan
 * Date: 3/23/20
 * Time: 2:49 PM
 */
public interface TokenInvitationOrganizationServiceFacade {

    TokenCreateResultResponse create(final CreateTokenInvitationOrganizationRequest request);
}
