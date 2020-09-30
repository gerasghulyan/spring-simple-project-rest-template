package com.vntana.core.rest.facade.user.component.precondition.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import com.vntana.core.model.user.request.GetByUuidsAndOrganizationUuidRequest;
import com.vntana.core.rest.facade.user.component.precondition.UserFacadePreconditionCheckerComponent;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.vntana.core.model.user.error.UserErrorResponseModel.*;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_UNPROCESSABLE_ENTITY;

/**
 * Created by Arman Gevorgyan.
 * Date: 4/7/20
 * Time: 6:52 PM
 */
@Component
public class UserFacadePreconditionCheckerComponentImpl implements UserFacadePreconditionCheckerComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserFacadePreconditionCheckerComponentImpl.class);

    private final UserService userService;
    private final OrganizationService organizationService;

    public UserFacadePreconditionCheckerComponentImpl(final UserService userService, final OrganizationService organizationService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.userService = userService;
        this.organizationService = organizationService;
    }

    @Override
    public SingleErrorWithStatus<UserErrorResponseModel> checkAccountDetails(final String userUuid) {
        LOGGER.debug("Processing precondition check for user facade account details where userUuid - {}", userUuid);
        if (StringUtils.isBlank(userUuid)) {
            return SingleErrorWithStatus.of(SC_UNPROCESSABLE_ENTITY, MISSING_UUID);
        }
        if (!userService.existsByUuid(userUuid)) {
            return SingleErrorWithStatus.of(SC_NOT_FOUND, NOT_FOUND_FOR_UUID);
        }
        return SingleErrorWithStatus.empty();
    }

    @Override
    public SingleErrorWithStatus<UserErrorResponseModel> checkGetByOrganizationUuid(final String organizationUuid) {
        LOGGER.debug("Processing precondition check for user facade get by organization uui where organizationUuid - {}", organizationUuid);
        if (StringUtils.isBlank(organizationUuid)) {
            return SingleErrorWithStatus.of(SC_UNPROCESSABLE_ENTITY, MISSING_UUID);
        }
        if (!organizationService.existsByUuid(organizationUuid)) {
            return SingleErrorWithStatus.of(SC_NOT_FOUND, NOT_FOUND_FOR_ORGANIZATION);
        }
        return SingleErrorWithStatus.empty();
    }

    @Override
    public SingleErrorWithStatus<UserErrorResponseModel> checkGetByUuidsAndOrganizationUuid(final GetByUuidsAndOrganizationUuidRequest request) {
        LOGGER.debug("Processing precondition check for user facade get by uuids and organization uuid for request - {}", request);
        if (!organizationService.existsByUuid(request.getOrganizationUuid())) {
            return SingleErrorWithStatus.of(SC_NOT_FOUND, NOT_FOUND_FOR_ORGANIZATION);
        }
        if (!userService.existsByUuids(request.getUuids())) {
            return SingleErrorWithStatus.of(SC_NOT_FOUND, NOT_FOUND_FOR_USERS_UUIDS);
        }
        return SingleErrorWithStatus.empty();
    }
}
