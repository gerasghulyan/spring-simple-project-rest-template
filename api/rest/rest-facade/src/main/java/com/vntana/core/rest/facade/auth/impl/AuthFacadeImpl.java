package com.vntana.core.rest.facade.auth.impl;

import com.vntana.core.domain.user.AbstractClientOrganizationAwareUserRole;
import com.vntana.core.domain.user.AbstractUserRole;
import com.vntana.core.model.auth.response.UserRoleModel;
import com.vntana.core.model.security.request.FindUserByUuidAndClientOrganizationRequest;
import com.vntana.core.model.security.request.FindUserByUuidAndOrganizationRequest;
import com.vntana.core.model.security.response.SecureFindUserByEmailResponse;
import com.vntana.core.model.security.response.SecureFindUserByUuidAndClientOrganizationResponse;
import com.vntana.core.model.security.response.SecureFindUserByUuidAndOrganizationResponse;
import com.vntana.core.model.security.response.model.SecureFindUserByEmailResponseModel;
import com.vntana.core.model.security.response.model.SecureUserClientOrganizationAwareResponseModel;
import com.vntana.core.model.security.response.model.SecureUserOrganizationAwareResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import com.vntana.core.model.user.request.FindUserByEmailRequest;
import com.vntana.core.persistence.utils.PersistenceUtilityService;
import com.vntana.core.rest.facade.auth.AuthFacade;
import com.vntana.core.service.user.UserService;
import com.vntana.core.service.user.role.UserRoleService;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.vntana.core.model.user.error.UserErrorResponseModel.*;

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
                .map(user -> {
                    final boolean isSuperAdminRole = user.roleOfSuperAdmin().isPresent();
                    return findUserRole(request, isSuperAdminRole)
                            .map(role -> {
                                final SecureUserOrganizationAwareResponseModel response = new SecureUserOrganizationAwareResponseModel(
                                        user.getUuid(),
                                        user.getEmail(),
                                        role,
                                        isSuperAdminRole,
                                        request.getOrganizationUuid()
                                );
                                LOGGER.debug("Successfully found user roles for findByUserAndOrganization request - {} with response - {}", request, response);
                                return new SecureFindUserByUuidAndOrganizationResponse(
                                        response
                                );
                            }).orElse(new SecureFindUserByUuidAndOrganizationResponse(HttpStatus.SC_NOT_FOUND, NOT_FOUND_FOR_ROLE));
                }).orElse(new SecureFindUserByUuidAndOrganizationResponse(HttpStatus.SC_NOT_FOUND, USER_NOT_FOUND));
        LOGGER.debug("Successfully processed auth facade findByUserAndOrganization for request - {}", request);
        return result;
    }

    @Transactional
    @Override
    public SecureFindUserByUuidAndClientOrganizationResponse findByUserAndClientOrganization(final FindUserByUuidAndClientOrganizationRequest request) {
        LOGGER.debug("Processing auth facade findByUserAndClientOrganization for request - {}", request);
        final SecureFindUserByUuidAndClientOrganizationResponse result = userService.findByUuid(request.getUuid())
                .map(theUser -> {
                    final boolean isSuperAdminRole = theUser.roleOfSuperAdmin().isPresent();
                    return userRoleService.findByClientOrganizationAndUser(request.getClientUuid(), request.getUuid())
                            .map(AbstractClientOrganizationAwareUserRole.class::cast)
                            .map(theClientOrganizationRole -> new SecureFindUserByUuidAndClientOrganizationResponse(
                                    new SecureUserClientOrganizationAwareResponseModel(
                                            theUser.getUuid(),
                                            theUser.getEmail(),
                                            isSuperAdminRole ? UserRoleModel.SUPER_ADMIN : UserRoleModel.valueOf(theClientOrganizationRole.getUserRole().name()),
                                            isSuperAdminRole,
                                            theClientOrganizationRole.getClientOrganization().getOrganization().getUuid(),
                                            theClientOrganizationRole.getClientOrganization().getUuid()
                                    )
                            )).orElse(new SecureFindUserByUuidAndClientOrganizationResponse(HttpStatus.SC_NOT_FOUND, NOT_FOUND_FOR_ROLE));
                }).orElse(new SecureFindUserByUuidAndClientOrganizationResponse(HttpStatus.SC_NOT_FOUND, USER_NOT_FOUND));
        LOGGER.debug("Successfully processed auth facade findByUserAndClientOrganization for request - {}", request);
        return result;
    }

    private Optional<UserRoleModel> findUserRole(final FindUserByUuidAndOrganizationRequest request, final boolean isSuperAdminRole) {
        if (isSuperAdminRole) {
            return Optional.of(UserRoleModel.SUPER_ADMIN);
        }
        return Optional.ofNullable(userRoleService.findByOrganizationAndUser(request.getOrganizationUuid(), request.getUuid())
                .map(theRole -> UserRoleModel.valueOf(theRole.getUserRole().name()))
                .orElseGet(() -> {
                    if (!CollectionUtils.isEmpty(userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(request.getOrganizationUuid(), request.getUuid()))) {
                        return UserRoleModel.ORGANIZATION_CLIENTS_VIEWER;
                    }
                    return null;
                }));
    }
}
