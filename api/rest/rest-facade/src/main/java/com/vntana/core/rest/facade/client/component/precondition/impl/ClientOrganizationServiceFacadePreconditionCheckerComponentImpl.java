package com.vntana.core.rest.facade.client.component.precondition.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel;
import com.vntana.core.rest.facade.client.component.precondition.ClientOrganizationServiceFacadePreconditionCheckerComponent;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Manuk Gharslyan.
 * Date: 11/18/2020
 * Time: 1:03 PM
 */
@Component
public class ClientOrganizationServiceFacadePreconditionCheckerComponentImpl implements ClientOrganizationServiceFacadePreconditionCheckerComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientOrganizationServiceFacadePreconditionCheckerComponentImpl.class);

    private final UserService userService;
    private final OrganizationService organizationService;

    ClientOrganizationServiceFacadePreconditionCheckerComponentImpl(final UserService userService, final OrganizationService organizationService) {
        this.userService = userService;
        this.organizationService = organizationService;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Override
    public SingleErrorWithStatus<ClientOrganizationErrorResponseModel> checkGetUserClientOrganizations(final String userUuid, final String organizationUuid) {
        LOGGER.debug("Processing precondition checkGetUserClientOrganizations for userUuid - {} and organizationUuid - {}", userUuid, organizationUuid);
        if (StringUtils.isBlank(userUuid)) {
            return SingleErrorWithStatus.of(HttpStatus.SC_UNPROCESSABLE_ENTITY, ClientOrganizationErrorResponseModel.MISSING_USER_UUID);
        }
        if (StringUtils.isBlank(organizationUuid)) {
            return SingleErrorWithStatus.of(HttpStatus.SC_UNPROCESSABLE_ENTITY, ClientOrganizationErrorResponseModel.MISSING_ORGANIZATION_UUID);
        }
        if (!userService.existsByUuid(userUuid)) {
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, ClientOrganizationErrorResponseModel.USER_NOT_FOUND);
        }
        if (!organizationService.existsByUuid(organizationUuid)) {
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, ClientOrganizationErrorResponseModel.ORGANIZATION_NOT_FOUND);
        }
        LOGGER.debug("Successfully processed precondition checkGetUserClientOrganizations for userUuid - {} and organizationUuid - {}", userUuid, organizationUuid);
        return SingleErrorWithStatus.empty();
    }
}