package com.vntana.core.rest.facade.user.external.impl;

import com.vntana.core.domain.user.external.ExternalUser;
import com.vntana.core.domain.user.external.ExternalUserSource;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import com.vntana.core.model.user.external.request.GetOrCreateExternalUserRequest;
import com.vntana.core.model.user.external.responce.GetOrCreateExternalUserResponse;
import com.vntana.core.model.user.external.responce.GetOrCreateExternalUserResponseModel;
import com.vntana.core.rest.facade.user.external.ExternalUserServiceFacade;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.user.external.ExternalUserService;
import com.vntana.core.service.user.external.dto.GetOrCreateExternalUserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static org.apache.http.HttpStatus.SC_CONFLICT;

/**
 * Created by Diana Gevorgyan
 * Date: 7/14/2021
 * Time: 4:51 PM
 */
@Component
public class ExternalUserServiceFacadeImpl implements ExternalUserServiceFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalUserServiceFacadeImpl.class);

    private final ExternalUserService externalUserService;
    private final OrganizationService organizationService;

    public ExternalUserServiceFacadeImpl(
            final ExternalUserService externalUserService,
            final OrganizationService organizationService) {
        this.externalUserService = externalUserService;
        this.organizationService = organizationService;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Override
    public GetOrCreateExternalUserResponse getOrCreateExternalUser(final GetOrCreateExternalUserRequest request) {
        LOGGER.debug("Getting external user for request - {}", request);
        final GetOrCreateExternalUserResponse result = organizationService.findByUuid(request.getOrganizationUuid())
                .map(org -> {
                    final ExternalUser externalUser = externalUserService.getOrCreate(
                            new GetOrCreateExternalUserDto(
                                    request.getExternalUuid(),
                                    //TODO change this source type in case of further integrations
                                    ExternalUserSource.OTTO,
                                    org));
                    return new GetOrCreateExternalUserResponse(
                            new GetOrCreateExternalUserResponseModel(
                                    externalUser.getTargetUser().getUuid(),
                                    externalUser.getExternalUuid()));
                })
                .orElseGet(() -> new GetOrCreateExternalUserResponse(SC_CONFLICT, UserErrorResponseModel.ORGANIZATION_NOT_FOUND));
        LOGGER.debug("Done getting external user with result - {}", result);
        return result;
    }
}
