package com.vntana.core.rest.facade.auth.impl;

import com.vntana.core.domain.token.TokenPersonalAccess;
import com.vntana.core.domain.user.AbstractUserRole;
import com.vntana.core.domain.user.User;
import com.vntana.core.model.auth.response.UserRoleModel;
import com.vntana.core.model.security.request.CreatePersonalAccessTokenRequest;
import com.vntana.core.model.security.request.FindUserByPersonalAccessTokenRequest;
import com.vntana.core.model.security.request.FindUserByUuidAndClientOrganizationRequest;
import com.vntana.core.model.security.request.FindUserByUuidAndOrganizationRequest;
import com.vntana.core.model.security.response.*;
import com.vntana.core.model.security.response.model.*;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import com.vntana.core.model.user.request.FindUserByEmailRequest;
import com.vntana.core.model.user.request.RegeneratePersonalAccessTokenRequest;
import com.vntana.core.persistence.utils.PersistenceUtilityService;
import com.vntana.core.rest.facade.auth.AuthFacade;
import com.vntana.core.service.token.personalaccess.PersonalAccessTokenService;
import com.vntana.core.service.token.personalaccess.dto.CreatePersonalAccessTokenDto;
import com.vntana.core.service.token.personalaccess.dto.RegeneratePersonalAccessTokenDto;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.vntana.core.model.user.error.UserErrorResponseModel.NOT_FOUND_FOR_ROLE;
import static com.vntana.core.model.user.error.UserErrorResponseModel.USER_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;

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
    private final PersonalAccessTokenService personalAccessTokenService;

    public AuthFacadeImpl(
            final UserService userService,
            final UserRoleService userRoleService,
            final PersistenceUtilityService persistenceUtilityService,
            final PersonalAccessTokenService personalAccessTokenService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.persistenceUtilityService = persistenceUtilityService;
        this.personalAccessTokenService = personalAccessTokenService;
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
                                    collectUserRoles(user)
                            )
                    ))
                    .orElseGet(() -> new SecureFindUserByEmailResponse(Collections.singletonList(UserErrorResponseModel.NOT_FOUND_FOR_EMAIL)));
            mutableResponse.setValue(response);
        });
        return mutableResponse.getValue();
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    @Override
    public SecureFindUserByUuidAndClientOrganizationResponse findByUserAndClientOrganization(final FindUserByUuidAndClientOrganizationRequest request) {
        LOGGER.debug("Processing auth facade findByUserAndClientOrganization for request - {}", request);
        final SecureFindUserByUuidAndClientOrganizationResponse result = userService.findByUuid(request.getUuid())
                .map(theUser -> userRoleService.findByClientOrganizationAndUser(request.getClientUuid(), request.getUuid())
                        .map(theClientOrganizationRole -> new SecureFindUserByUuidAndClientOrganizationResponse(
                                new SecureUserClientOrganizationAwareResponseModel(
                                        theUser.getUuid(),
                                        theUser.getEmail(),
                                        UserRoleModel.valueOf(theClientOrganizationRole.getUserRole().name()),
                                        false,
                                        theClientOrganizationRole.getClientOrganization().getOrganization().getUuid(),
                                        theClientOrganizationRole.getClientOrganization().getUuid()
                                )
                        ))
                        .orElse(new SecureFindUserByUuidAndClientOrganizationResponse(HttpStatus.SC_NOT_FOUND, NOT_FOUND_FOR_ROLE)))
                .orElse(new SecureFindUserByUuidAndClientOrganizationResponse(HttpStatus.SC_NOT_FOUND, USER_NOT_FOUND));
        LOGGER.debug("Successfully processed auth facade findByUserAndClientOrganization for request - {}", request);
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public SecureFindUserByPersonalAccessTokenResponse findByPersonalAccessToken(final FindUserByPersonalAccessTokenRequest request) {
        LOGGER.debug("Processing auth facade findByPersonalAccessTokenToken method for request - {}", request);
        final Optional<TokenPersonalAccess> foundToken = personalAccessTokenService.findByToken(request.getToken());
        if (foundToken.isPresent()) {
            LOGGER.debug("Successfully processed personal access token facade findByToken method");
            return new SecureFindUserByPersonalAccessTokenResponse(
                    new SecureFindUserByPersonalAccessTokenResponseModel(
                            foundToken.get().getUser().getUuid(),
                            foundToken.get().getUser().getEmail(),
                            collectUserRoles(foundToken.get().getUser())));
        }
        LOGGER.debug("Cannot process personal access token facade findByToken method");
        return new SecureFindUserByPersonalAccessTokenResponse(SC_FORBIDDEN, UserErrorResponseModel.TOKEN_NOT_FOUND);
    }

    @Override
    public PersonalAccessTokenResponse createPersonalAccessToken(final CreatePersonalAccessTokenRequest request) {
        LOGGER.debug("Processing auth token facade create personal access token for user uuid - {}", request.getUserUuid());
        final Optional<User> foundUser = userService.findByUuid(request.getUserUuid());
        if (!foundUser.isPresent()) {
            LOGGER.debug("Cannot processes auth facade create personal access token for user uuid - {}, cannot find user", request.getUserUuid());
            return new PersonalAccessTokenResponse(SC_NOT_FOUND, UserErrorResponseModel.USER_NOT_FOUND);
        }
        final TokenPersonalAccess pat = personalAccessTokenService.create(new CreatePersonalAccessTokenDto(request.getUserUuid(), request.getToken()));
        final PersonalAccessTokenResponse response = new PersonalAccessTokenResponse(pat.getToken(), pat.getUser().getUuid());
        LOGGER.debug("Successfully processed auth token facade create personal access token with response - {} ", response);
        return response;
    }

    @Override
    public PersonalAccessTokenExpireResponse expirePersonalAccessTokenByUserUuid(final String userUuid) {
        LOGGER.debug("Processing auth facade expire personal access token by userUuid for user uuid - {}", userUuid);
        final Optional<User> foundUser = userService.findByUuid(userUuid);
        if (!foundUser.isPresent()) {
            LOGGER.debug("Cannot processes personal access token facade expireByUserUuid for user uuid - {}, cannot find user", userUuid);
            return new PersonalAccessTokenExpireResponse(SC_NOT_FOUND, UserErrorResponseModel.USER_NOT_FOUND);
        }
        final Optional<TokenPersonalAccess> foundToken = personalAccessTokenService.findByUser(userUuid);
        foundToken.ifPresent(tokenPersonalAccess -> personalAccessTokenService.expire(tokenPersonalAccess.getUuid()));
        final PersonalAccessTokenExpireResponse response = new PersonalAccessTokenExpireResponse(new PersonalAccessTokenExpireResponseModel(userUuid));
        LOGGER.debug("Successfully processed auth facade expire personal access token with response - {}", response);
        return response;
    }

    @Override
    public PersonalAccessTokenResponse findPersonalAccessTokenByUserUuid(final String userUuid) {
        LOGGER.debug("Processing auth token facade findPersonalAccessTokenByUserUuid for user uuid - {}", userUuid);
        final Optional<User> foundUser = userService.findByUuid(userUuid);
        if (!foundUser.isPresent()) {
            LOGGER.debug("Cannot process auth facade findPersonalAccessTokenByUserUuid for user uuid - {}, cannot find user", userUuid);
            return new PersonalAccessTokenResponse(SC_NOT_FOUND, UserErrorResponseModel.USER_NOT_FOUND);
        }
        final Optional<TokenPersonalAccess> foundToken = personalAccessTokenService.findByUser(userUuid);
        if (foundToken.isPresent()) {
            final PersonalAccessTokenResponse response = new PersonalAccessTokenResponse(foundToken.get().getToken(), foundToken.get().getUser().getUuid());
            LOGGER.debug("Successfully processed auth facade findPersonalAccessTokenByUserUuid with response - {} ", response);
            return response;
        }
        LOGGER.debug("Cannot process auth facade findPersonalAccessTokenByUserUuid for user uuid - {}", userUuid);
        return new PersonalAccessTokenResponse(SC_NOT_FOUND, UserErrorResponseModel.TOKEN_NOT_FOUND);
    }

    @Override
    public PersonalAccessTokenResponse regeneratePersonalAccessToken(final RegeneratePersonalAccessTokenRequest request) {
        LOGGER.debug("Processing auth facade regenerate for request - {}", request);
        final Optional<User> foundUser = userService.findByUuid(request.getUserUuid());
        if (!foundUser.isPresent()) {
            LOGGER.debug("Cannot processes auth facade regenerate for request - {}, cannot find user", request.getUserUuid());
            return new PersonalAccessTokenResponse(SC_NOT_FOUND, UserErrorResponseModel.USER_NOT_FOUND);
        }
        final TokenPersonalAccess pat = personalAccessTokenService.regenerateTokenForUser(new RegeneratePersonalAccessTokenDto(request.getUserUuid(), request.getToken()));
        final PersonalAccessTokenResponse response = new PersonalAccessTokenResponse(pat.getToken(), pat.getUser().getUuid());
        LOGGER.debug("Successfully processed auth facade regenerate with response - {} ", response);
        return response;
    }

    private List<UserRoleModel> collectUserRoles(final User user) {
        return user.roles().stream()
                .map(AbstractUserRole::getUserRole)
                .map(Enum::name)
                .map(UserRoleModel::valueOf)
                .collect(Collectors.toList());
    }

    private Optional<UserRoleModel> findUserRole(final FindUserByUuidAndOrganizationRequest request, final boolean isSuperAdminRole) {
        if (isSuperAdminRole) {
            return Optional.of(UserRoleModel.SUPER_ADMIN);
        }
        return Optional.ofNullable(userRoleService.findByOrganizationAndUser(request.getOrganizationUuid(), request.getUuid())
                .map(theRole -> UserRoleModel.valueOf(theRole.getUserRole().name()))
                .orElseGet(() -> {
                    if (!CollectionUtils.isEmpty(userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(request.getOrganizationUuid(), request.getUuid()))) {
                        return UserRoleModel.ORGANIZATION_CLIENT_MEMBER;
                    }
                    return null;
                }));
    }
}
