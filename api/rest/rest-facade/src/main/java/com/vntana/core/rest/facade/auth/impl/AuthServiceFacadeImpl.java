package com.vntana.core.rest.facade.auth.impl;

import com.vntana.core.model.auth.response.UserRoleModel;
import com.vntana.core.model.security.request.FindUserByEmailAndOrganizationRequest;
import com.vntana.core.model.security.response.SecureFindUserByEmailAndOrganizationResponse;
import com.vntana.core.model.security.response.SecureFindUserByEmailResponse;
import com.vntana.core.model.security.response.model.SecureFindUserByEmailAndOrganizationResponseModel;
import com.vntana.core.model.security.response.model.SecureFindUserByEmailResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import com.vntana.core.model.user.request.FindUserByEmailRequest;
import com.vntana.core.persistence.utils.PersistenceUtilityService;
import com.vntana.core.rest.facade.auth.AuthServiceFacade;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.user.UserService;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Created by Geras Ghulyan.
 * Date: 10/1/19
 * Time: 6:35 PM
 */
@Component
public class AuthServiceFacadeImpl implements AuthServiceFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceFacadeImpl.class);

    private final UserService userService;

    private final OrganizationService organizationService;

    private final PersistenceUtilityService persistenceUtilityService;

    public AuthServiceFacadeImpl(
            final UserService userService,
            final OrganizationService organizationService,
            final PersistenceUtilityService persistenceUtilityService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.userService = userService;
        this.organizationService = organizationService;
        this.persistenceUtilityService = persistenceUtilityService;
    }

    @Override
    public SecureFindUserByEmailResponse findByEmail(final FindUserByEmailRequest request) {
        final Mutable<SecureFindUserByEmailResponse> mutableResponse = new MutableObject<>();
        persistenceUtilityService.runInPersistenceSession(() -> {
            final SecureFindUserByEmailResponse response = userService.findByEmail(request.getEmail())
                    .map(user -> new SecureFindUserByEmailResponse(
                            new SecureFindUserByEmailResponseModel(
                                    user.getUuid(),
                                    user.getEmail(),
                                    user.getPassword()
                            )
                    ))
                    .orElseGet(() -> new SecureFindUserByEmailResponse(Collections.singletonList(UserErrorResponseModel.NOT_FOUND_FOR_EMAIL)));
            mutableResponse.setValue(response);
        });
        return mutableResponse.getValue();
    }

    @Override
    public SecureFindUserByEmailAndOrganizationResponse findByEmailAndOrganization(final FindUserByEmailAndOrganizationRequest request) {
        final Mutable<SecureFindUserByEmailAndOrganizationResponse> mutableResponse = new MutableObject<>();
        persistenceUtilityService.runInPersistenceSession(() -> {
            final SecureFindUserByEmailAndOrganizationResponse response = userService.findByEmail(request.getEmail())
                    .map(user -> organizationService.findByUuid(request.getOrganizationUuid())
                            .map(organization -> user.roleOfOrganization(organization)
                                    .map(userOrganizationRole -> UserRoleModel.valueOf(userOrganizationRole.getUserRole().name()))
                                    .map(userRoleModel -> new SecureFindUserByEmailAndOrganizationResponse(
                                                    new SecureFindUserByEmailAndOrganizationResponseModel(
                                                            user.getUuid(),
                                                            user.getEmail(),
                                                            userRoleModel,
                                                            user.getPassword()
                                                    )
                                            )
                                    ).orElseGet(() -> errorFindByEmailAndOrganization(UserErrorResponseModel.NOT_FOUND_FOR_ROLE)))
                            .orElseGet(() -> errorFindByEmailAndOrganization(UserErrorResponseModel.NOT_FOUND_FOR_ORGANIZATION)))
                    .orElseGet(() -> errorFindByEmailAndOrganization(UserErrorResponseModel.NOT_FOUND_FOR_EMAIL));
            mutableResponse.setValue(response);
        });
        return mutableResponse.getValue();
    }

    private SecureFindUserByEmailAndOrganizationResponse errorFindByEmailAndOrganization(final UserErrorResponseModel notFoundForOrganization) {
        return new SecureFindUserByEmailAndOrganizationResponse(Collections.singletonList(notFoundForOrganization));
    }
}
