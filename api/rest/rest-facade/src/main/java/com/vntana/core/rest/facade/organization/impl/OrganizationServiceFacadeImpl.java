package com.vntana.core.rest.facade.organization.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.user.User;
import com.vntana.core.domain.user.UserClientOrganizationRole;
import com.vntana.core.domain.user.UserOrganizationOwnerRole;
import com.vntana.core.domain.user.UserRole;
import com.vntana.core.model.auth.response.UserRoleModel;
import com.vntana.core.model.organization.error.OrganizationErrorResponseModel;
import com.vntana.core.model.organization.request.CheckAvailableOrganizationSlugRequest;
import com.vntana.core.model.organization.request.CreateOrganizationRequest;
import com.vntana.core.model.organization.response.CheckAvailableOrganizationSlugResultResponse;
import com.vntana.core.model.organization.response.create.CreateOrganizationResultResponse;
import com.vntana.core.model.organization.response.get.GetOrganizationBySlugResponseModel;
import com.vntana.core.model.organization.response.get.GetOrganizationBySlugResultResponse;
import com.vntana.core.model.organization.response.get.GetOrganizationByUuidResponseModel;
import com.vntana.core.model.organization.response.get.GetOrganizationByUuidResultResponse;
import com.vntana.core.model.organization.response.get.model.OrganizationStatusModel;
import com.vntana.core.model.organization.response.invitation.GetOrganizationInvitationByOrganizationResponse;
import com.vntana.core.model.organization.response.invitation.GetOrganizationInvitationByOrganizationResponseModel;
import com.vntana.core.model.organization.response.update.request.UpdateOrganizationRequest;
import com.vntana.core.model.organization.response.update.response.UpdateOrganizationResultResponse;
import com.vntana.core.model.user.response.UserOrganizationResponse;
import com.vntana.core.model.user.response.get.model.GetUserOrganizationsGridResponseModel;
import com.vntana.core.model.user.response.get.model.GetUserOrganizationsResponseModel;
import com.vntana.core.persistence.utils.PersistenceUtilityService;
import com.vntana.core.rest.facade.organization.OrganizationServiceFacade;
import com.vntana.core.rest.facade.organization.component.precondition.OrganizationServiceFacadePreconditionCheckerComponent;
import com.vntana.core.service.common.component.SlugValidationComponent;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.organization.dto.CreateOrganizationDto;
import com.vntana.core.service.organization.dto.GetAllOrganizationDto;
import com.vntana.core.service.organization.dto.UpdateOrganizationDto;
import com.vntana.core.service.organization.mediator.OrganizationLifecycleMediator;
import com.vntana.core.service.organization.mediator.OrganizationUuidAwareLifecycleMediator;
import com.vntana.core.service.user.UserService;
import com.vntana.core.service.user.dto.UserGrantOrganizationRoleDto;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 3:04 PM
 */
@Component
public class OrganizationServiceFacadeImpl implements OrganizationServiceFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationServiceFacadeImpl.class);

    private final MapperFacade mapperFacade;
    private final OrganizationService organizationService;
    private final UserService userService;
    private final PersistenceUtilityService persistenceUtilityService;
    private final OrganizationLifecycleMediator organizationLifecycleMediator;
    private final OrganizationUuidAwareLifecycleMediator organizationUuidAwareLifecycleMediator;
    private final SlugValidationComponent slugValidationComponent;
    private final OrganizationServiceFacadePreconditionCheckerComponent organizationServiceFacadePreconditionChecker;

    public OrganizationServiceFacadeImpl(
            final MapperFacade mapperFacade,
            final OrganizationService organizationService,
            final UserService userService,
            final PersistenceUtilityService persistenceUtilityService,
            final OrganizationLifecycleMediator organizationLifecycleMediator,
            final OrganizationUuidAwareLifecycleMediator organizationUuidAwareLifecycleMediator,
            final SlugValidationComponent slugValidationComponent,
            final OrganizationServiceFacadePreconditionCheckerComponent organizationServiceFacadePreconditionChecker) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.userService = userService;
        this.mapperFacade = mapperFacade;
        this.organizationService = organizationService;
        this.persistenceUtilityService = persistenceUtilityService;
        this.organizationLifecycleMediator = organizationLifecycleMediator;
        this.organizationUuidAwareLifecycleMediator = organizationUuidAwareLifecycleMediator;
        this.slugValidationComponent = slugValidationComponent;
        this.organizationServiceFacadePreconditionChecker = organizationServiceFacadePreconditionChecker;
    }

    @Override
    public CheckAvailableOrganizationSlugResultResponse checkSlugAvailability(
            final CheckAvailableOrganizationSlugRequest request) {
        final SingleErrorWithStatus<OrganizationErrorResponseModel> errorResponse = validateSlugErrors(request.getSlug());
        if (errorResponse.isPresent()) {
            return new CheckAvailableOrganizationSlugResultResponse(errorResponse.getHttpStatus(), errorResponse.getError());
        }
        final Mutable<String> mutableSlug = new MutableObject<>(request.getSlug());
        final MutableInt mutableInt = new MutableInt(1);
        while (organizationService.findBySlug(mutableSlug.getValue()).isPresent()) {
            LOGGER.debug("Organization with slug - {} already exists, trying to generate suggested one", mutableSlug.getValue());
            mutableSlug.setValue(format("%s%d", request.getSlug(), mutableInt.getAndIncrement()));
        }
        return new CheckAvailableOrganizationSlugResultResponse(mutableSlug.getValue().equals(request.getSlug()), mutableSlug.getValue());
    }

    @Override
    public CreateOrganizationResultResponse create(final CreateOrganizationRequest request) {
        final SingleErrorWithStatus<OrganizationErrorResponseModel> errorResponse = validateSlugErrors(request.getSlug());
        if (errorResponse.isPresent()) {
            return new CreateOrganizationResultResponse(errorResponse.getHttpStatus(), errorResponse.getError());
        }
        return organizationService.findBySlug(request.getSlug())
                .map(organization -> {
                    LOGGER.debug("Organization already exists for slug - {}", request.getSlug());
                    return new CreateOrganizationResultResponse(HttpStatus.SC_UNPROCESSABLE_ENTITY, OrganizationErrorResponseModel.SLUG_ALREADY_EXISTS);
                })
                .orElseGet(() -> {
                    LOGGER.debug("Creating organization for request - {}", request);
                    final CreateOrganizationDto dto = mapperFacade.map(request, CreateOrganizationDto.class);
                    final Mutable<String> mutableResponse = new MutableObject<>();
                    persistenceUtilityService.runInNewTransaction(() -> {
                        final Organization organization = organizationService.create(dto);
                        userService.grantOrganizationRole(new UserGrantOrganizationRoleDto(
                                request.getUserUuid(),
                                organization.getUuid(),
                                UserRole.ORGANIZATION_OWNER)
                        );
                        organizationLifecycleMediator.onCreated(organization);
                        organizationUuidAwareLifecycleMediator.onCreated(organization.getUuid());
                        mutableResponse.setValue(organization.getUuid());
                    });
                    return new CreateOrganizationResultResponse(mutableResponse.getValue());
                });
    }

    @Override
    public UserOrganizationResponse getUserOrganizations(final String userUuid) {
        if (StringUtils.isBlank(userUuid)) {
            return new UserOrganizationResponse(HttpStatus.SC_UNPROCESSABLE_ENTITY, OrganizationErrorResponseModel.MISSING_USER_UUID);
        }
        LOGGER.debug("Retrieving user organizations by user uuid - {}", userUuid);
        final Mutable<List<GetUserOrganizationsResponseModel>> mutableResponse = new MutableObject<>();
        final Mutable<OrganizationErrorResponseModel> mutableErrorResponse = new MutableObject<>();
        persistenceUtilityService.runInNewTransaction(() -> {
            final Optional<User> userOptional = userService.findByUuid(userUuid);
            if (!userOptional.isPresent()) {
                mutableErrorResponse.setValue(OrganizationErrorResponseModel.USER_NOT_FOUND);
                return;
            }
            final User user = userOptional.get();
            final List<GetUserOrganizationsResponseModel> response = user.roleOfSuperAdmin()
                    .map(userSuperAdminRole -> getOrganizationsWhenSuperAdmin(userUuid))
                    .orElseGet(() -> getOrganizationsWhenNotSuperAdmin(user));
            mutableResponse.setValue(response);
        });
        if (Objects.nonNull(mutableErrorResponse.getValue())) {
            return new UserOrganizationResponse(HttpStatus.SC_NOT_FOUND, mutableErrorResponse.getValue());
        }
        final List<GetUserOrganizationsResponseModel> response = mutableResponse.getValue();
        return new UserOrganizationResponse(new GetUserOrganizationsGridResponseModel(response.size(), response));
    }

    @Override
    public GetOrganizationBySlugResultResponse getBySlug(final String slug) {
        if (StringUtils.isBlank(slug)) {
            return new GetOrganizationBySlugResultResponse(HttpStatus.SC_UNPROCESSABLE_ENTITY, OrganizationErrorResponseModel.MISSING_SLUG);
        }
        LOGGER.debug("Retrieving organization by slug - {}", slug);
        final Optional<Organization> optionalOrganization = organizationService.findBySlug(slug);
        if (!optionalOrganization.isPresent()) {
            return new GetOrganizationBySlugResultResponse(HttpStatus.SC_NOT_FOUND, OrganizationErrorResponseModel.SLUG_NOT_FOUND);
        }
        final Organization organization = optionalOrganization.get();
        LOGGER.debug("Successfully retrieved organization with result - {}", organization);
        final GetOrganizationBySlugResponseModel response = new GetOrganizationBySlugResponseModel(
                organization.getUuid(),
                organization.getName(),
                organization.getSlug(),
                organization.getImageBlobId()
        );
        return new GetOrganizationBySlugResultResponse(response);
    }

    @Override
    public GetOrganizationByUuidResultResponse getByUuid(final String uuid) {
        if (StringUtils.isBlank(uuid)) {
            return new GetOrganizationByUuidResultResponse(HttpStatus.SC_UNPROCESSABLE_ENTITY, OrganizationErrorResponseModel.MISSING_UUID);
        }
        LOGGER.debug("Retrieving organization by uuid - {}", uuid);
        if (!organizationService.existsByUuid(uuid)) {
            return new GetOrganizationByUuidResultResponse(HttpStatus.SC_NOT_FOUND, OrganizationErrorResponseModel.ORGANIZATION_NOT_FOUND);
        }
        final Organization organization = organizationService.getByUuid(uuid);
        LOGGER.debug("Successfully retrieved organization with result - {}", organization);
        final GetOrganizationByUuidResponseModel response = new GetOrganizationByUuidResponseModel(
                organization.getUuid(),
                organization.getName(),
                organization.getSlug(),
                organization.getImageBlobId(),
                OrganizationStatusModel.valueOf(organization.getStatus().name()),
                organization.getCreated()
        );
        return new GetOrganizationByUuidResultResponse(response);
    }

    @Transactional
    @Override
    public UpdateOrganizationResultResponse update(final UpdateOrganizationRequest request) {
        LOGGER.debug("Processing organization facade update method for request - {}", request);
        if (!organizationService.existsByUuid(request.getUuid())) {
            return new UpdateOrganizationResultResponse(HttpStatus.SC_NOT_FOUND, OrganizationErrorResponseModel.ORGANIZATION_NOT_FOUND);
        }
        final Organization organization = organizationService.update(mapperFacade.map(request, UpdateOrganizationDto.class));
        organizationLifecycleMediator.onUpdated(organization);
        organizationUuidAwareLifecycleMediator.onUpdated(organization.getUuid());
        LOGGER.debug("Successfully processed organization facade update method for request - {}", request);
        return new UpdateOrganizationResultResponse(organization.getUuid());
    }

    @Transactional
    @Override
    public GetOrganizationInvitationByOrganizationResponse getOrganizationInvitation(final String organizationUuid) {
        LOGGER.debug("Retrieving invitation organization having organizationUuid - {}", organizationUuid);
        final SingleErrorWithStatus<OrganizationErrorResponseModel> error = organizationServiceFacadePreconditionChecker.checkGetOrganizationInvitation(organizationUuid);
        if (error.isPresent()) {
            return new GetOrganizationInvitationByOrganizationResponse(error.getHttpStatus(), error.getError());
        }
        final Organization organization = organizationService.getByUuid(organizationUuid);
        return new GetOrganizationInvitationByOrganizationResponse(
                new GetOrganizationInvitationByOrganizationResponseModel(organizationUuid, organization.getInvitation().getCustomerSubscriptionDefinitionUuid())
        );
    }

    private List<GetUserOrganizationsResponseModel> getOrganizationsWhenNotSuperAdmin(final User user) {
        LOGGER.debug("Retrieving user organizations for not system admin user with uuid - {}", user.getUuid());
        return user.roles()
                .stream()
                .map(userRole -> {
                    LOGGER.debug("Retrieving user organizations for not super admin user with uuid - {} and role - {}", user.getUuid(), userRole.getUserRole().name());
                    switch (userRole.getUserRole()) {
                        case ORGANIZATION_OWNER:
                            final UserOrganizationOwnerRole userOrganizationOwnerRole = (UserOrganizationOwnerRole) userRole;
                            return new GetUserOrganizationsResponseModel(
                                    userOrganizationOwnerRole.getOrganization().getUuid(),
                                    userOrganizationOwnerRole.getOrganization().getSlug(),
                                    userOrganizationOwnerRole.getOrganization().getName(),
                                    UserRoleModel.valueOf(userOrganizationOwnerRole.getUserRole().name()),
                                    userOrganizationOwnerRole.getOrganization().getImageBlobId(),
                                    userOrganizationOwnerRole.getOrganization().getCreated()
                            );
                        case CLIENT_ADMIN:
                            final UserClientOrganizationRole userClientOrganizationRole = (UserClientOrganizationRole) userRole;
                            return new GetUserOrganizationsResponseModel(
                                    userClientOrganizationRole.getClientOrganization().getOrganization().getUuid(),
                                    userClientOrganizationRole.getClientOrganization().getOrganization().getSlug(),
                                    userClientOrganizationRole.getClientOrganization().getOrganization().getName(),
                                    UserRoleModel.valueOf(userClientOrganizationRole.getUserRole().name()),
                                    userClientOrganizationRole.getClientOrganization().getOrganization().getImageBlobId(),
                                    userClientOrganizationRole.getClientOrganization().getOrganization().getCreated()
                            );
                        default:
                            throw new IllegalStateException(format("Unknown user role %s", userRole.toString()));
                    }
                })
                .collect(Collectors.toList());
    }

    private List<GetUserOrganizationsResponseModel> getOrganizationsWhenSuperAdmin(final String userUuid) {
        LOGGER.debug("Retrieving user organizations for system admin user with uuid - {}", userUuid);
        return organizationService.getAll(
                new GetAllOrganizationDto(organizationService.count().intValue())
        ).stream()
                .map(organization -> new GetUserOrganizationsResponseModel(
                        organization.getUuid(),
                        organization.getSlug(),
                        organization.getName(),
                        UserRoleModel.SUPER_ADMIN,
                        organization.getImageBlobId(),
                        organization.getCreated()
                ))
                .collect(Collectors.toList());
    }

    private SingleErrorWithStatus<OrganizationErrorResponseModel> validateSlugErrors(final String slug) {
        if (!slugValidationComponent.validate(slug)) {
            return SingleErrorWithStatus.of(HttpStatus.SC_UNPROCESSABLE_ENTITY, OrganizationErrorResponseModel.SLUG_NOT_VALID);
        }
        return SingleErrorWithStatus.empty();
    }
}
