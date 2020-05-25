package com.vntana.core.rest.facade.organization.component.precondition.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.domain.organization.Organization;
import com.vntana.core.model.organization.error.OrganizationErrorResponseModel;
import com.vntana.core.rest.facade.organization.component.precondition.OrganizationServiceFacadePreconditionCheckerComponent;
import com.vntana.core.service.organization.OrganizationService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Geras Ghulyan
 * Date: 26.04.20
 * Time: 23:11
 */
@Component
public class OrganizationServiceFacadePreconditionCheckerComponentImpl implements OrganizationServiceFacadePreconditionCheckerComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationServiceFacadePreconditionCheckerComponentImpl.class);

    private final OrganizationService organizationService;

    public OrganizationServiceFacadePreconditionCheckerComponentImpl(final OrganizationService organizationService) {
        this.organizationService = organizationService;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Override
    public SingleErrorWithStatus<OrganizationErrorResponseModel> checkGetOrganizationInvitation(final String organizationUuid) {
        LOGGER.debug("Processing precondition checkGetOrganizationInvitation where organizationUuid - {}", organizationUuid);
        if (StringUtils.isEmpty(organizationUuid)) {
            return SingleErrorWithStatus.of(HttpStatus.SC_UNPROCESSABLE_ENTITY, OrganizationErrorResponseModel.MISSING_UUID);
        }
        LOGGER.debug("Retrieving organization by uuid - {}", organizationUuid);
        if (!organizationService.existsByUuid(organizationUuid)) {
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, OrganizationErrorResponseModel.ORGANIZATION_NOT_FOUND);
        }
        final Organization organization = organizationService.getByUuid(organizationUuid);
        if (!organization.hasBeenCreatedFromInvitation()) {
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, OrganizationErrorResponseModel.ORGANIZATION_INVITATION_NOT_FOUND);
        }
        return SingleErrorWithStatus.empty();
    }
}
