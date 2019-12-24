package com.vntana.core.rest.facade.auth.impl;

import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.user.User;
import com.vntana.core.model.auth.response.UserRoleModel;
import com.vntana.core.model.security.request.FindUserByUuidAndOrganizationRequest;
import com.vntana.core.model.security.response.SecureFindUserByUuidAndOrganizationResponse;
import com.vntana.core.model.security.response.SecureFindUserByEmailResponse;
import com.vntana.core.model.security.response.model.SecureUserOrganizationResponseModel;
import com.vntana.core.model.security.response.model.SecureFindUserByEmailResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import com.vntana.core.model.user.request.FindUserByEmailRequest;
import com.vntana.core.persistence.utils.PersistenceUtilityService;
import com.vntana.core.rest.facade.auth.AuthFacade;
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
public class AuthFacadeImpl implements AuthFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthFacadeImpl.class);

    private final UserService userService;

    private final OrganizationService organizationService;

    private final PersistenceUtilityService persistenceUtilityService;

    public AuthFacadeImpl(
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
    public SecureFindUserByUuidAndOrganizationResponse findByUserAndOrganization(final FindUserByUuidAndOrganizationRequest request) {
        final Mutable<SecureFindUserByUuidAndOrganizationResponse> mutableResponse = new MutableObject<>();
        persistenceUtilityService.runInPersistenceSession(() -> {
            final User user = userService.getByUuid(request.getUuid());
            final Organization organization = organizationService.getByUuid(request.getOrganizationUuid());
            if (user.roleOfSuperAdmin().isPresent()) {
                final SecureFindUserByUuidAndOrganizationResponse response = new SecureFindUserByUuidAndOrganizationResponse(
                        new SecureUserOrganizationResponseModel(
                                user.getUuid(),
                                user.getEmail(),
                                UserRoleModel.SUPER_ADMIN,
                                organization.getUuid()
                        )
                );
                mutableResponse.setValue(response);
            } else {
                final SecureFindUserByUuidAndOrganizationResponse response = user.roleOfOrganization(organization)
                        .map(userOrganizationRole -> UserRoleModel.valueOf(userOrganizationRole.getUserRole().name()))
                        .map(userRoleModel -> new SecureFindUserByUuidAndOrganizationResponse(
                                        new SecureUserOrganizationResponseModel(
                                                user.getUuid(),
                                                user.getEmail(),
                                                userRoleModel,
                                                organization.getUuid()
                                        )
                                )
                        ).orElseGet(() -> errorFindByEmailAndOrganization(UserErrorResponseModel.NOT_FOUND_FOR_ROLE));
                mutableResponse.setValue(response);
            }
        });
        return mutableResponse.getValue();
    }

    private SecureFindUserByUuidAndOrganizationResponse errorFindByEmailAndOrganization(final UserErrorResponseModel notFoundForOrganization) {
        return new SecureFindUserByUuidAndOrganizationResponse(Collections.singletonList(notFoundForOrganization));
    }
}
