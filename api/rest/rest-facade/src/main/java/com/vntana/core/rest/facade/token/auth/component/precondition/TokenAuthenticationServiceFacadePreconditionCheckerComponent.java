package com.vntana.core.rest.facade.token.auth.component.precondition;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.model.token.auth.error.TokenAuthenticationErrorResponseModel;
import com.vntana.core.model.token.auth.request.TokenAuthenticationPersistWithClientOrganizationRequest;
import com.vntana.core.model.token.auth.request.TokenAuthenticationPersistWithOrganizationRequest;

/**
 * Created by Manuk Gharslyan.
 * Date: 11/13/2020
 * Time: 3:51 PM
 */
public interface TokenAuthenticationServiceFacadePreconditionCheckerComponent {

    SingleErrorWithStatus<TokenAuthenticationErrorResponseModel> checkPersistWithOrganization(final TokenAuthenticationPersistWithOrganizationRequest request);

    SingleErrorWithStatus<TokenAuthenticationErrorResponseModel> checkPersistWithClientOrganization(final TokenAuthenticationPersistWithClientOrganizationRequest request);
}