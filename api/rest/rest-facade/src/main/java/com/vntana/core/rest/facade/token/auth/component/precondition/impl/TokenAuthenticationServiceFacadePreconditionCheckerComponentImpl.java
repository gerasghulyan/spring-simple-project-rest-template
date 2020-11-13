package com.vntana.core.rest.facade.token.auth.component.precondition.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.model.token.auth.error.TokenAuthenticationErrorResponseModel;
import com.vntana.core.model.token.auth.request.TokenAuthenticationPersistWithClientOrganizationRequest;
import com.vntana.core.model.token.auth.request.TokenAuthenticationPersistWithOrganizationRequest;
import com.vntana.core.rest.facade.token.auth.component.precondition.TokenAuthenticationServiceFacadePreconditionCheckerComponent;
import com.vntana.core.service.client.ClientOrganizationService;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.user.UserService;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Manuk Gharslyan.
 * Date: 11/13/2020
 * Time: 3:54 PM
 */
@Component
public class TokenAuthenticationServiceFacadePreconditionCheckerComponentImpl implements TokenAuthenticationServiceFacadePreconditionCheckerComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenAuthenticationServiceFacadePreconditionCheckerComponentImpl.class);

    private final UserService userService;
    private final OrganizationService organizationService;
    private final ClientOrganizationService clientOrganizationService;

    public TokenAuthenticationServiceFacadePreconditionCheckerComponentImpl(final UserService userService, final OrganizationService organizationService, final ClientOrganizationService clientOrganizationService) {
        this.userService = userService;
        this.organizationService = organizationService;
        this.clientOrganizationService = clientOrganizationService;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Override
    public SingleErrorWithStatus<TokenAuthenticationErrorResponseModel> checkPersistWithOrganization(final TokenAuthenticationPersistWithOrganizationRequest request) {
        LOGGER.debug("Checking authentication token persisting with organization precondition for request - {}", request);
        if (!userService.existsByUuid(request.getUserUuid())) {
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, TokenAuthenticationErrorResponseModel.USER_NOT_FOUND);
        }
        if (!organizationService.existsByUuid(request.getUserUuid())) {
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, TokenAuthenticationErrorResponseModel.ORGANIZATION_NOT_FOUND);
        }
        LOGGER.debug("Successfully done checking authentication token persisting with organization precondition for request - {}", request);
        return SingleErrorWithStatus.empty();
    }

    @Override
    public SingleErrorWithStatus<TokenAuthenticationErrorResponseModel> checkPersistWithClientOrganization(final TokenAuthenticationPersistWithClientOrganizationRequest request) {
        LOGGER.debug("Checking authentication token persisting with client organization precondition for request - {}", request);
        if (!userService.existsByUuid(request.getUserUuid())) {
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, TokenAuthenticationErrorResponseModel.USER_NOT_FOUND);
        }
        if (!clientOrganizationService.existsByUuid(request.getUserUuid())) {
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, TokenAuthenticationErrorResponseModel.CLIENT_ORGANIZATION_NOT_FOUND);
        }
        LOGGER.debug("Successfully done checking authentication token persisting with client organization precondition for request - {}", request);
        return SingleErrorWithStatus.empty();
    }
}