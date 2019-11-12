package com.vntana.core.rest.facade.user.impl;

import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.template.email.TemplateEmail;
import com.vntana.core.domain.template.email.TemplateEmailType;
import com.vntana.core.domain.user.User;
import com.vntana.core.domain.user.UserRole;
import com.vntana.core.model.auth.response.UserRoleModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import com.vntana.core.model.user.request.CreateUserRequest;
import com.vntana.core.model.user.request.FindUserByEmailRequest;
import com.vntana.core.model.user.request.SendUserVerificationRequest;
import com.vntana.core.model.user.response.*;
import com.vntana.core.model.user.response.model.AccountUserResponseModel;
import com.vntana.core.model.user.response.model.CreateUserResponseModel;
import com.vntana.core.model.user.response.model.FindUserByEmailResponseModel;
import com.vntana.core.notification.EmailSenderService;
import com.vntana.core.notification.payload.verification.VerificationEmailSendPayload;
import com.vntana.core.persistence.utils.PersistenceUtilityService;
import com.vntana.core.rest.facade.user.UserServiceFacade;
import com.vntana.core.service.email.EmailValidationComponent;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.organization.dto.CreateOrganizationDto;
import com.vntana.core.service.template.email.TemplateEmailService;
import com.vntana.core.service.user.UserService;
import com.vntana.core.service.user.dto.CreateUserDto;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
    private final EmailSenderService emailSenderService;
    private final TemplateEmailService templateEmailService;

    @Value("${verification.website.url}")
    private String verificationUrlPrefix;

    public UserServiceFacadeImpl(final UserService userService,
                                 final OrganizationService organizationService,
                                 final PersistenceUtilityService persistenceUtilityService,
                                 final EmailValidationComponent emailValidationComponent,
                                 final EmailSenderService emailSenderService,
                                 final TemplateEmailService templateEmailService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.userService = userService;
        this.organizationService = organizationService;
        this.persistenceUtilityService = persistenceUtilityService;
        this.emailValidationComponent = emailValidationComponent;
        this.emailSenderService = emailSenderService;
        this.templateEmailService = templateEmailService;
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
        final Mutable<String> mutableUserUuid = new MutableObject<>();
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
            mutableUserUuid.setValue(user.getUuid());
            LOGGER.debug("Successfully created user - {} for request - {}", user, request);
        });
        return new CreateUserResponse(new CreateUserResponseModel(mutableUserUuid.getValue()));
    }

    @Override
    public FindUserByEmailResponse findByEmail(final FindUserByEmailRequest request) {
        return userService.findByEmail(request.getEmail())
                .map(user -> new FindUserByEmailResponse(
                        new FindUserByEmailResponseModel(
                                true,
                                user.getEmail(),
                                UserRoleModel.ASSET_MANAGER
                        )
                ))
                .orElseGet(() -> new FindUserByEmailResponse(Collections.singletonList(UserErrorResponseModel.NOT_FOUND_FOR_EMAIL)));
    }

    @Override
    public AccountUserResponse accountDetails(final String uuid, final String organizationUuid) {
        final Mutable<AccountUserResponse> mutableResponse = new MutableObject<>();
        persistenceUtilityService.runInPersistenceSession(() -> {
            final User user = userService.getByUuid(uuid);
            final AccountUserResponse response = user.roleOfSuperAdmin()
                    .map(userSuperAdminRole -> new AccountUserResponse(new AccountUserResponseModel(
                            user.getUuid(),
                            user.getFullName(),
                            user.getEmail(),
                            UserRoleModel.SUPER_ADMIN))
                    )
                    .orElseGet(() -> {
                        final Organization organization = organizationService.getByUuid(organizationUuid);
                        return user.roleOfOrganization(organization)
                                .map(userOrganizationRole -> UserRoleModel.valueOf(userOrganizationRole.getUserRole().name()))
                                .map(userRoleModel -> new AccountUserResponse(
                                                new AccountUserResponseModel(
                                                        user.getUuid(),
                                                        user.getFullName(),
                                                        user.getEmail(),
                                                        userRoleModel
                                                )
                                        )
                                ).orElseGet(this::errorFindByEmailAndOrganization);
                    });
            mutableResponse.setValue(response);
        });
        return mutableResponse.getValue();
    }

    private AccountUserResponse errorFindByEmailAndOrganization() {
        return new AccountUserResponse(Collections.singletonList(UserErrorResponseModel.NOT_FOUND_FOR_ORGANIZATION));
    }


    @Override
    public VerifyUserResponse verify(final String uuid) {
        LOGGER.debug("Processing user facade verify method for uuid - {}", uuid);
        final List<UserErrorResponseModel> errorsList = checkVerifyForPossibleErrors(uuid);
        if (!errorsList.isEmpty()) {
            return new VerifyUserResponse(errorsList);
        }
        final User user = userService.makeVerified(uuid);
        LOGGER.debug("Successfully processed user facade verify method for uuid - {}", uuid);
        return new VerifyUserResponse(user.getUuid());
    }

    @Override
    public SendUserVerificationResponse sendVerificationEmail(final SendUserVerificationRequest request) {
        final Optional<User> userOptional = userService.findByUuid(request.getUuid());
        if (!userOptional.isPresent()) {
            return new SendUserVerificationResponse(Collections.singletonList(UserErrorResponseModel.NOT_FOUND_FOR_UUID));
        }
        final User user = userOptional.get();
        if (user.getVerified()) {
            return new SendUserVerificationResponse(Collections.singletonList(UserErrorResponseModel.USER_ALREADY_VERIFIED));
        }
        final TemplateEmail templateEmail = templateEmailService.getByType(TemplateEmailType.USER_VERIFICATION);
        final VerificationEmailSendPayload payload = new VerificationEmailSendPayload(
                templateEmail.getTemplateName(),
                user.getEmail(),
                "Confirm your e-mail address",
                String.format("%s/%s", verificationUrlPrefix, request.getToken())
        );
        emailSenderService.sendEmail(payload);
        return new SendUserVerificationResponse(request.getUuid());
    }

    private List<UserErrorResponseModel> checkVerifyForPossibleErrors(final String uuid) {
        if (StringUtils.isBlank(uuid)) {
            return Collections.singletonList(UserErrorResponseModel.MISSING_UUID);
        }
        final Optional<User> user = userService.findByUuid(uuid);
        if (!user.isPresent()) {
            return Collections.singletonList(UserErrorResponseModel.NOT_FOUND_FOR_UUID);
        }
        if (user.get().getVerified()) {
            return Collections.singletonList(UserErrorResponseModel.USER_ALREADY_VERIFIED);
        }
        return Collections.emptyList();
    }
}
