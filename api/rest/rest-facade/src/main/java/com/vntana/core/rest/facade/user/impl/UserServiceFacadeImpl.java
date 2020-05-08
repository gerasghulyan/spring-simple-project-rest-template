package com.vntana.core.rest.facade.user.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.commons.persistence.domain.AbstractUuidAwareDomainEntity;
import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.user.AbstractUserRole;
import com.vntana.core.domain.user.User;
import com.vntana.core.domain.user.UserOrganizationOwnerRole;
import com.vntana.core.domain.user.UserRole;
import com.vntana.core.model.auth.response.UserRoleModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import com.vntana.core.model.user.request.*;
import com.vntana.core.model.user.response.*;
import com.vntana.core.model.user.response.account.AccountUserResponse;
import com.vntana.core.model.user.response.account.model.AccountUserResponseModel;
import com.vntana.core.model.user.response.account.model.AccountUserRolesModel;
import com.vntana.core.model.user.response.get.GetUsersByOrganizationResponse;
import com.vntana.core.model.user.response.get.GetUsersByRoleAndOrganizationUuidResponse;
import com.vntana.core.model.user.response.get.model.GetUsersByOrganizationGridResponseModel;
import com.vntana.core.model.user.response.get.model.GetUsersByOrganizationResponseModel;
import com.vntana.core.model.user.response.get.model.GetUsersByRoleAndOrganizationUuidGridResponseModel;
import com.vntana.core.model.user.response.get.model.GetUsersByRoleAndOrganizationUuidResponseModel;
import com.vntana.core.model.user.response.model.CreateUserResponseModel;
import com.vntana.core.model.user.response.model.FindUserByEmailResponseModel;
import com.vntana.core.model.user.response.model.FindUserByUuidResponseModel;
import com.vntana.core.model.user.response.model.ResetUserPasswordResponseModel;
import com.vntana.core.persistence.utils.PersistenceUtilityService;
import com.vntana.core.rest.facade.user.UserServiceFacade;
import com.vntana.core.rest.facade.user.component.UserResetPasswordEmailSenderComponent;
import com.vntana.core.rest.facade.user.component.UserVerificationSenderComponent;
import com.vntana.core.rest.facade.user.component.precondition.UserFacadePreconditionCheckerComponent;
import com.vntana.core.service.email.EmailValidationComponent;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.organization.dto.CreateOrganizationDto;
import com.vntana.core.service.organization.dto.GetUserOrganizationsByUserUuidAndRoleDto;
import com.vntana.core.service.organization.mediator.OrganizationLifecycleMediator;
import com.vntana.core.service.user.UserService;
import com.vntana.core.service.user.dto.CreateUserDto;
import com.vntana.core.service.user.dto.UpdateUserDto;
import com.vntana.core.service.user.role.UserRoleService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.vntana.core.model.user.error.UserErrorResponseModel.*;
import static org.apache.http.HttpStatus.SC_UNPROCESSABLE_ENTITY;

/**
 * Created by Arthur Asatryan.
 * Date: 10/1/19
 * Time: 6:35 PM
 */
@Component
public class UserServiceFacadeImpl implements UserServiceFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceFacadeImpl.class);

    private final UserService userService;
    private final UserRoleService userRoleService;
    private final OrganizationService organizationService;
    private final PersistenceUtilityService persistenceUtilityService;
    private final UserFacadePreconditionCheckerComponent preconditionCheckerComponent;
    private final EmailValidationComponent emailValidationComponent;
    private final UserVerificationSenderComponent verificationSenderComponent;
    private final UserResetPasswordEmailSenderComponent resetPasswordEmailSenderComponent;
    private final OrganizationLifecycleMediator organizationLifecycleMediator;

    public UserServiceFacadeImpl(final UserService userService,
                                 final UserRoleService userRoleService,
                                 final OrganizationService organizationService,
                                 final PersistenceUtilityService persistenceUtilityService,
                                 final UserFacadePreconditionCheckerComponent preconditionCheckerComponent,
                                 final EmailValidationComponent emailValidationComponent,
                                 final UserVerificationSenderComponent verificationSenderComponent,
                                 final UserResetPasswordEmailSenderComponent resetPasswordEmailSenderComponent,
                                 final OrganizationLifecycleMediator organizationLifecycleMediator) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.organizationService = organizationService;
        this.persistenceUtilityService = persistenceUtilityService;
        this.preconditionCheckerComponent = preconditionCheckerComponent;
        this.emailValidationComponent = emailValidationComponent;
        this.verificationSenderComponent = verificationSenderComponent;
        this.resetPasswordEmailSenderComponent = resetPasswordEmailSenderComponent;
        this.organizationLifecycleMediator = organizationLifecycleMediator;
    }

    @Override
    public CreateUserResponse create(final CreateUserRequest request) {
        LOGGER.debug("Processing Facade createUser method for request - {}", request);
        Assert.notNull(request, "The UserCreateRequest should not be null");
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
                    new CreateOrganizationDto(request.getOrganizationName(), request.getOrganizationSlug(), null)
            );
            final User user = userService.create(new CreateUserDto(
                    request.getFullName(),
                    request.getEmail(),
                    request.getPassword(),
                    organization.getUuid(),
                    UserRole.ORGANIZATION_OWNER
            ));
            organizationLifecycleMediator.onCreated(organization);
            mutableResponse.setValue(new CreateUserResponseModel(user.getUuid(), organization.getUuid()));
            LOGGER.debug("Successfully created user - {} for request - {}", user, request);
        });
        return new CreateUserResponse(mutableResponse.getValue());
    }

    @Override
    public ExistsUserByEmailResponse existsByEmail(final String email) {
        LOGGER.debug("Processing user facade existsByEmail for email - {}", email);
        if (StringUtils.isBlank(email)) {
            return new ExistsUserByEmailResponse(SC_UNPROCESSABLE_ENTITY, UserErrorResponseModel.MISSING_EMAIL);
        }
        final boolean exists = userService.existsByEmail(email);
        LOGGER.debug("Successfully processed user facade existsByEmail for email - {}", email);
        return new ExistsUserByEmailResponse(exists);
    }

    @Override
    public FindUserByEmailResponse findByEmail(final FindUserByEmailRequest request) {
        return userService.findByEmail(request.getEmail())
                .map(user -> new FindUserByEmailResponse(
                        new FindUserByEmailResponseModel(
                                true,
                                user.getEmail(),
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

    @Transactional
    @Override
    public AccountUserResponse accountDetails(final String uuid) {
        LOGGER.debug("Processing user facade accountDetails for uuid - {}", uuid);
        final SingleErrorWithStatus<UserErrorResponseModel> error = preconditionCheckerComponent.checkAccountDetails(uuid);
        if (error.isPresent()) {
            return new AccountUserResponse(error.getHttpStatus(), error.getError());
        }
        final User user = userService.getByUuid(uuid);
        final AccountUserRolesModel rolesModel = new AccountUserRolesModel();
        rolesModel.setSuperAdmin(user.roleOfSuperAdmin().isPresent());
        rolesModel.setOwnerInOrganization(user.immutableOrganizationRoles().stream()
                .map(UserOrganizationOwnerRole::getOrganization)
                .map(AbstractUuidAwareDomainEntity::getUuid)
                .collect(Collectors.toList()));
        final AccountUserResponseModel responseModel = new AccountUserResponseModel();
        responseModel.setRoles(rolesModel);
        responseModel.setUuid(user.getUuid());
        responseModel.setFullName(user.getFullName());
        responseModel.setEmail(user.getEmail());
        responseModel.setEmailVerified(user.getVerified());
        responseModel.setImageBlobId(user.getImageBlobId());
        return new AccountUserResponse(responseModel);
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

    @Transactional
    @Override
    public UpdateUserResponse update(final UpdateUserRequest request) {
        Assert.notNull(request, "The UpdateUserRequest should not be null");
        LOGGER.debug("Processing facade update method for request - {}", request);
        if (!userService.existsByUuid(request.getUuid())) {
            return new UpdateUserResponse(UserErrorResponseModel.USER_NOT_FOUND);
        }
        final User updatedUser = userService.update(new UpdateUserDto(
                request.getUuid(),
                request.getImageBlobId(),
                request.getFullName()
        ));
        fireUserOwnedOrganizationsOnUpdateEvent(updatedUser.getUuid());
        LOGGER.debug("Successfully processed facade update method for request - {}", request);
        return new UpdateUserResponse(updatedUser.getUuid());
    }

    @Override
    public ChangeUserPasswordResponse changePassword(final ChangeUserPasswordRequest request) {
        Assert.notNull(request, "The ChangeUserPasswordRequest should not be null");
        LOGGER.debug("Processing facade changePassword method for user having uuid - {}", request.getUuid());
        if (!userService.existsByUuid(request.getUuid())) {
            return new ChangeUserPasswordResponse(UserErrorResponseModel.USER_NOT_FOUND);
        }
        if (!userService.checkPassword(request.getUuid(), request.getOldPassword())) {
            return new ChangeUserPasswordResponse(UserErrorResponseModel.PASSWORD_MISMATCH);
        }
        final User user = userService.changePassword(request.getUuid(), request.getNewPassword());
        LOGGER.debug("Successfully processed facade changePassword method for user having uuid - {}", request.getUuid());
        return new ChangeUserPasswordResponse(user.getUuid());
    }

    @Override
    public GetUsersByRoleAndOrganizationUuidResponse getByRoleAndOrganizationUuid(final UserRoleModel userRole, final String organizationUuid) {
        LOGGER.debug("Processing facade getByRoleAndOrganizationUuid method for user role - {} and organization uuid - {}", userRole, organizationUuid);
        final SingleErrorWithStatus<UserErrorResponseModel> possibleErrors = checkGetByRoleAndOrganizationUuidPossibleErrors(userRole, organizationUuid);
        if (possibleErrors.isPresent()) {
            return new GetUsersByRoleAndOrganizationUuidResponse(possibleErrors.getHttpStatus(), possibleErrors.getError());
        }
        final List<User> response = userService.findByRoleAndOrganizationUuid(UserRole.valueOf(userRole.name()), organizationUuid);
        if (response.isEmpty()) {
            return new GetUsersByRoleAndOrganizationUuidResponse(HttpStatus.SC_NOT_FOUND, Arrays.asList(NOT_FOUND_FOR_ROLE, NOT_FOUND_FOR_ORGANIZATION));
        }
        if (userRole == UserRoleModel.ORGANIZATION_OWNER && response.size() > 1) {
            return new GetUsersByRoleAndOrganizationUuidResponse(HttpStatus.SC_CONFLICT, ORGANIZATION_ROLE_CONFLICT);
        }
        LOGGER.debug("Successfully processed facade getByRoleAndOrganizationUuid method for user role - {} and organization uuid - {}", userRole, organizationUuid);
        return new GetUsersByRoleAndOrganizationUuidResponse(new GetUsersByRoleAndOrganizationUuidGridResponseModel(
                response.size(),
                response.stream()
                        .map(user -> new GetUsersByRoleAndOrganizationUuidResponseModel(
                                user.getUuid(),
                                user.getFullName(),
                                user.getEmail(),
                                user.getImageBlobId())
                        ).collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList))
        ));
    }

    @Transactional
    @Override
    public GetUsersByOrganizationResponse getByOrganizationUuid(final String organizationUuid) {
        LOGGER.debug("Processing user facade getByOrganizationUuid for organizationUuid - {}", organizationUuid);
        final SingleErrorWithStatus<UserErrorResponseModel> error = preconditionCheckerComponent.checkGetByOrganizationUuid(organizationUuid);
        if (error.isPresent()) {
            return new GetUsersByOrganizationResponse(error.getHttpStatus(), error.getError());
        }
        final List<AbstractUserRole> userRoles = userRoleService.findAllByOrganizationUuid(organizationUuid);
        final GetUsersByOrganizationGridResponseModel responseModel = userRoles.stream().map(userRole -> {
            final User user = userRole.getUser();
            return new GetUsersByOrganizationResponseModel(
                    user.getUuid(),
                    user.getFullName(),
                    user.getEmail(),
                    user.getImageBlobId(),
                    UserRoleModel.valueOf(userRole.getUserRole().name())
            );
        }).collect(Collectors.collectingAndThen(Collectors.toList(), GetUsersByOrganizationGridResponseModel::new));
        LOGGER.debug("Successfully processed user facade getByOrganizationUuid for organizationUuid - {}", organizationUuid);
        return new GetUsersByOrganizationResponse(responseModel);
    }

    private SingleErrorWithStatus<UserErrorResponseModel> checkGetByRoleAndOrganizationUuidPossibleErrors(final UserRoleModel userRole, final String organizationUuid) {
        if (userRole == null) {
            return SingleErrorWithStatus.of(SC_UNPROCESSABLE_ENTITY, UserErrorResponseModel.MISSING_USER_ROLE);
        }
        if (StringUtils.isBlank(organizationUuid)) {
            return SingleErrorWithStatus.of(SC_UNPROCESSABLE_ENTITY, UserErrorResponseModel.MISSING_ORGANIZATION);
        }
        if (!organizationService.existsByUuid(organizationUuid)) {
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, ORGANIZATION_NOT_FOUND);
        }
        return SingleErrorWithStatus.empty();
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

    private void fireUserOwnedOrganizationsOnUpdateEvent(final String userUuid) {
        organizationService.getUserOrganizationsByUserUuidAndRole(new GetUserOrganizationsByUserUuidAndRoleDto(
                        userUuid,
                        UserRole.ORGANIZATION_OWNER
                )
        ).forEach(organizationLifecycleMediator::onUpdated);
    }
}
