package com.vntana.core.rest.facade.user.impl;

import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.user.User;
import com.vntana.core.domain.user.UserRole;
import com.vntana.core.model.auth.response.UserRoleModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import com.vntana.core.model.user.request.CreateUserRequest;
import com.vntana.core.model.user.request.FindUserByEmailRequest;
import com.vntana.core.model.user.response.CreateUserResponse;
import com.vntana.core.model.user.response.FindUserByEmailResponse;
import com.vntana.core.model.user.response.model.CreateUserResponseModel;
import com.vntana.core.model.user.response.model.FindUserByEmailResponseModel;
import com.vntana.core.persistence.utils.PersistenceUtilityService;
import com.vntana.core.rest.facade.user.UserServiceFacade;
import com.vntana.core.service.email.EmailValidationComponent;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.organization.dto.CreateOrganizationDto;
import com.vntana.core.service.user.UserService;
import com.vntana.core.service.user.dto.CreateUserDto;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Collections;

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

    public UserServiceFacadeImpl(final UserService userService,
                                 final OrganizationService organizationService,
                                 final PersistenceUtilityService persistenceUtilityService,
                                 final EmailValidationComponent emailValidationComponent) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.userService = userService;
        this.organizationService = organizationService;
        this.persistenceUtilityService = persistenceUtilityService;
        this.emailValidationComponent = emailValidationComponent;
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
}
