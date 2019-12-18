package com.vntana.core.rest.facade.user.impl;

import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.user.User;
import com.vntana.core.domain.user.UserRole;
import com.vntana.core.model.auth.response.UserRoleModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import com.vntana.core.model.user.request.*;
import com.vntana.core.model.user.response.*;
import com.vntana.core.model.user.response.model.*;
import com.vntana.core.persistence.utils.PersistenceUtilityService;
import com.vntana.core.rest.facade.user.UserServiceFacade;
import com.vntana.core.rest.facade.user.component.UserResetPasswordEmailSenderComponent;
import com.vntana.core.rest.facade.user.component.UserVerificationSenderComponent;
import com.vntana.core.service.email.EmailValidationComponent;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.organization.dto.CreateOrganizationDto;
import com.vntana.core.service.user.UserService;
import com.vntana.core.service.user.dto.CreateUserDto;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by Arthur Asatryan.
 * Date: 10/1/19
 * Time: 6:35 PM
 */
@Component
public class UserServiceFacadeImpl implements UserServiceFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceFacadeImpl.class);

    private final UserService userService;
    private final OrganizationService organizationService;
    private final PersistenceUtilityService persistenceUtilityService;
    private final EmailValidationComponent emailValidationComponent;
    private final UserVerificationSenderComponent verificationSenderComponent;
    private final UserResetPasswordEmailSenderComponent resetPasswordEmailSenderComponent;

    public UserServiceFacadeImpl(final UserService userService,
                                 final OrganizationService organizationService,
                                 final PersistenceUtilityService persistenceUtilityService,
                                 final EmailValidationComponent emailValidationComponent,
                                 final UserVerificationSenderComponent verificationSenderComponent,
                                 final UserResetPasswordEmailSenderComponent resetPasswordEmailSenderComponent) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.userService = userService;
        this.organizationService = organizationService;
        this.persistenceUtilityService = persistenceUtilityService;
        this.emailValidationComponent = emailValidationComponent;
        this.verificationSenderComponent = verificationSenderComponent;
        this.resetPasswordEmailSenderComponent = resetPasswordEmailSenderComponent;
    }

    @Override
    public CreateUserResponse create(final CreateUserRequest request) {
        LOGGER.debug("Processing Facade createUser method for request - {}", request);
        Assert.notNull(request, "The USerCreateRequest should not be null");
        if (!emailValidationComponent.isValid(request.getEmail())) {
            return new CreateUserResponse(Collections.singletonList(UserErrorResponseModel.INVALID_EMAIL_FORMAT));
        }
        if (userService.findByEmail(request.getEmail()).isPresent()) {
            return new CreateUserResponse(Collections.singletonList(UserErrorResponseModel.SIGN_UP_WITH_EXISTING_EMAIL));
        }
        final Mutable<CreateUserResponseModel> mutableResponse = new MutableObject<>();
        persistenceUtilityService.runInNewTransaction(() -> {
            LOGGER.debug("Creating a organization for request - {}", request);
            final Organization organization = organizationService.create(
                    new CreateOrganizationDto(request.getOrganizationName(), request.getOrganizationSlug())
            );
            final User user = userService.create(new CreateUserDto(
                    request.getFullName(),
                    request.getEmail(),
                    request.getPassword(),
                    organization.getUuid(),
                    UserRole.ORGANIZATION_ADMIN
            ));
            mutableResponse.setValue(new CreateUserResponseModel(user.getUuid(), organization.getUuid()));
            LOGGER.debug("Successfully created user - {} for request - {}", user, request);
        });
        verificationSenderComponent.sendVerificationEmail(new SendUserVerificationRequest(request.getEmail(), request.getToken()));
        return new CreateUserResponse(mutableResponse.getValue());
    }

    @Override
    public FindUserByEmailResponse findByEmail(final FindUserByEmailRequest request) {
        return userService.findByEmail(request.getEmail())
                .map(user -> new FindUserByEmailResponse(
                        new FindUserByEmailResponseModel(
                                true,
                                user.getEmail(),
                                UserRoleModel.ASSET_MANAGER,
                                user.getUuid()
                        )
                ))
                .orElseGet(() -> new FindUserByEmailResponse(Collections.singletonList(UserErrorResponseModel.NOT_FOUND_FOR_EMAIL)));
    }

    @Override
    public FindUserByUuidResponse findByUuid(final String uuid) {
        LOGGER.debug("Processing facade findByUuid method for uuid - {}", uuid);
        if (StringUtils.isEmpty(uuid)) {
            return new FindUserByUuidResponse(UserErrorResponseModel.MISSING_UUID);
        }
        final FindUserByUuidResponse response = userService.findByUuid(uuid)
                .map(user -> new FindUserByUuidResponse(new FindUserByUuidResponseModel(
                        user.getUuid(),
                        user.getEmail()
                )))
                .orElseGet(() -> new FindUserByUuidResponse(UserErrorResponseModel.NOT_FOUND_FOR_UUID));
        LOGGER.debug("Successfully processed facade findByUuid method for uuid - {}", uuid);
        return response;
    }

    @Override
    public AccountUserResponse accountDetails(final String uuid, final String organizationUuid) {
        final Mutable<AccountUserResponse> mutableResponse = new MutableObject<>();
        persistenceUtilityService.runInPersistenceSession(() -> {
            final User user = userService.getByUuid(uuid);
            //TODO avatarUUID and HasSubscription implementation later
            final AccountUserResponse response = user.roleOfSuperAdmin()
                    .map(userSuperAdminRole -> new AccountUserResponse(new AccountUserResponseModel(
                            user.getUuid(),
                            user.getFullName(),
                            user.getEmail(),
                            UserRoleModel.SUPER_ADMIN,
                            user.getVerified(),
                            null,
                            false
                    )))
                    .orElseGet(() -> {
                        final Organization organization = organizationService.getByUuid(organizationUuid);
                        return user.roleOfOrganization(organization)
                                .map(userOrganizationRole -> UserRoleModel.valueOf(userOrganizationRole.getUserRole().name()))
                                .map(userRoleModel -> new AccountUserResponse(new AccountUserResponseModel(
                                        user.getUuid(),
                                        user.getFullName(),
                                        user.getEmail(),
                                        userRoleModel,
                                        user.getVerified(),
                                        null,
                                        false
                                )))
                                .orElseGet(() -> new AccountUserResponse(Collections.singletonList(UserErrorResponseModel.NOT_FOUND_FOR_ORGANIZATION)));
                    });
            mutableResponse.setValue(response);
        });
        return mutableResponse.getValue();
    }

    @Override
    public VerifyUserResponse verify(final String email) {
        LOGGER.debug("Processing user facade verify method for email - {}", email);
        final List<UserErrorResponseModel> errorsList = checkVerifyForPossibleErrors(email);
        if (!errorsList.isEmpty()) {
            return new VerifyUserResponse(errorsList);
        }
        final User user = userService.makeVerified(email);
        LOGGER.debug("Successfully processed user facade verify method for email - {}", email);
        return new VerifyUserResponse(user.getUuid());
    }

    @Override
    public SendUserVerificationResponse sendVerificationEmail(final SendUserVerificationRequest request) {
        LOGGER.debug("Processing facade sendVerificationEmail for request - {}", request);
        final SendUserVerificationResponse sendUserVerificationResponse = verificationSenderComponent.sendVerificationEmail(request);
        LOGGER.debug("Successfully processed facade sendVerificationEmail for request - {}", request);
        return sendUserVerificationResponse;
    }

    @Override
    public SendUserResetPasswordResponse sendResetPasswordEmail(final SendUserResetPasswordRequest request) {
        LOGGER.debug("Processing facade sendResetPasswordEmail for request - {}", request);
        final Optional<User> userOptional = userService.findByEmail(request.getEmail());
        if (!userOptional.isPresent()) {
            return new SendUserResetPasswordResponse(Collections.singletonList(UserErrorResponseModel.NOT_FOUND_FOR_EMAIL));
        }
        resetPasswordEmailSenderComponent.sendResetPasswordEmail(request.getEmail(), request.getToken());
        LOGGER.debug("Successfully processed facade sendResetPasswordEmail for request - {}", request);
        return new SendUserResetPasswordResponse();
    }

    @Override
    public ResetUserPasswordResponse resetPassword(final ResetUserPasswordRequest request) {
        LOGGER.debug("Processing facade resetPassword for email - {}", request.getEmail());
        final ResetUserPasswordResponse response = userService.findByEmail(request.getEmail())
                .map(user -> userService.changePassword(user.getUuid(), request.getPassword()).getEmail())
                .map(email -> new ResetUserPasswordResponse(new ResetUserPasswordResponseModel(email)))
                .orElse(new ResetUserPasswordResponse(Collections.singletonList(UserErrorResponseModel.NOT_FOUND_FOR_EMAIL)));
        LOGGER.debug("Successfully processed facade resetPassword for email - {}", request.getEmail());
        return response;
    }

    private List<UserErrorResponseModel> checkVerifyForPossibleErrors(final String email) {
        if (StringUtils.isBlank(email)) {
            return Collections.singletonList(UserErrorResponseModel.MISSING_EMAIL);
        }
        final Optional<User> user = userService.findByEmail(email);
        if (!user.isPresent()) {
            return Collections.singletonList(UserErrorResponseModel.NOT_FOUND_FOR_EMAIL);
        }
        if (Boolean.TRUE.equals(user.get().getVerified())) {
            return Collections.singletonList(UserErrorResponseModel.USER_ALREADY_VERIFIED);
        }
        return Collections.emptyList();
    }
}
