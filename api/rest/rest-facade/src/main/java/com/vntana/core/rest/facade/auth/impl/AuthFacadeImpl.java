package com.vntana.core.rest.facade.auth.impl;

import com.vntana.core.domain.user.AbstractUserRole;
import com.vntana.core.model.auth.response.UserRoleModel;
import com.vntana.core.model.security.request.FindUserByUuidAndOrganizationRequest;
import com.vntana.core.model.security.response.SecureFindUserByEmailResponse;
import com.vntana.core.model.security.response.SecureFindUserByUuidAndOrganizationResponse;
import com.vntana.core.model.security.response.model.SecureFindUserByEmailResponseModel;
import com.vntana.core.model.security.response.model.SecureUserOrganizationResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import com.vntana.core.model.user.request.FindUserByEmailRequest;
import com.vntana.core.persistence.utils.PersistenceUtilityService;
import com.vntana.core.rest.facade.auth.AuthFacade;
import com.vntana.core.service.user.UserService;
import com.vntana.core.service.user.role.UserRoleService;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.vntana.core.model.user.error.UserErrorResponseModel.NOT_FOUND_FOR_ROLE;

/**
 * Created by Geras Ghulyan.
 * Date: 10/1/19
 * Time: 6:35 PM
 */
@Component
public class AuthFacadeImpl implements AuthFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthFacadeImpl.class);

    private final UserService userService;
    private final UserRoleService userRoleService;
    private final PersistenceUtilityService persistenceUtilityService;

    public AuthFacadeImpl(final UserService userService,
                          final UserRoleService userRoleService,
                          final PersistenceUtilityService persistenceUtilityService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.userService = userService;
        this.userRoleService = userRoleService;
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
                                    user.getPassword(),
                                    user.roles().stream()
                                            .map(AbstractUserRole::getUserRole)
                                            .map(Enum::name)
                                            .map(UserRoleModel::valueOf)
                                            .collect(Collectors.toList())
                            )
                    ))
                    .orElseGet(() -> new SecureFindUserByEmailResponse(Collections.singletonList(UserErrorResponseModel.NOT_FOUND_FOR_EMAIL)));
            mutableResponse.setValue(response);
        });
        return mutableResponse.getValue();
    }

    @Transactional
    @Override
    public SecureFindUserByUuidAndOrganizationResponse findByUserAndOrganization(final FindUserByUuidAndOrganizationRequest request) {
        LOGGER.debug("Processing auth facade findByUserAndOrganization for request - {}", request);
        final SecureFindUserByUuidAndOrganizationResponse result = userService.findByUuid(request.getUuid())
                .flatMap(user -> {
                    final boolean isSuperAdminRole = user.roleOfSuperAdmin().isPresent();
                    return findUserRole(request, isSuperAdminRole)
                            .map(role -> {
                                final SecureUserOrganizationResponseModel response = new SecureUserOrganizationResponseModel(
                                        user.getUuid(),
                                        user.getEmail(),
                                        role,
                                        request.getOrganizationUuid(),
                                        isSuperAdminRole
                                );
                                LOGGER.debug("Successfully found user roles for findByUserAndOrganization request - {} with response - {}", request, response);
                                return new SecureFindUserByUuidAndOrganizationResponse(
                                        response
                                );
                            });
                })
                .orElse(new SecureFindUserByUuidAndOrganizationResponse(Collections.singletonList(NOT_FOUND_FOR_ROLE)));
        LOGGER.debug("Successfully processed auth facade findByUserAndOrganization for request - {}", request);
        return result;
    }

    private Optional<UserRoleModel> findUserRole(final FindUserByUuidAndOrganizationRequest request, final boolean isSuperAdminRole) {
        if (isSuperAdminRole) {
            return Optional.of(UserRoleModel.SUPER_ADMIN);
        }
        return Optional.ofNullable(userRoleService.findByOrganizationAndUser(request.getOrganizationUuid(), request.getUuid())
                .map(theRole -> UserRoleModel.valueOf(theRole.getUserRole().name()))
                .orElseGet(() -> {
                    if (userRoleService.existsClientOrganizationRoleByOrganizationAndUser(request.getOrganizationUuid(), request.getUuid())) {
                        return UserRoleModel.ORGANIZATION_CLIENTS_VIEWER;
                    }
                    return null;
                }));
    }
}
