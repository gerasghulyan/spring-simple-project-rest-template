package com.vntana.core.rest.facade.client.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.domain.client.ClientOrganization;
import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.user.User;
import com.vntana.core.domain.user.UserOrganizationAdminRole;
import com.vntana.core.domain.user.UserOrganizationOwnerRole;
import com.vntana.core.domain.user.UserRole;
import com.vntana.core.model.auth.response.UserRoleModel;
import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel;
import com.vntana.core.model.client.request.CheckAvailableClientOrganizationSlugRequest;
import com.vntana.core.model.client.request.CreateClientOrganizationRequest;
import com.vntana.core.model.client.request.UpdateClientOrganizationRequest;
import com.vntana.core.model.client.response.CheckAvailableClientOrganizationSlugResponseModel;
import com.vntana.core.model.client.response.CheckAvailableClientOrganizationSlugResultResponse;
import com.vntana.core.model.client.response.CreateClientOrganizationResultResponse;
import com.vntana.core.model.client.response.UpdateClientOrganizationResultResponse;
import com.vntana.core.model.client.response.get.*;
import com.vntana.core.model.user.response.UserClientBulkOrganizationResponse;
import com.vntana.core.model.user.response.UserClientOrganizationResponse;
import com.vntana.core.model.user.response.get.model.GetUserClientBulkOrganizationsGridResponseModel;
import com.vntana.core.model.user.response.get.model.GetUserClientOrganizationsResponseModel;
import com.vntana.core.rest.facade.client.ClientOrganizationServiceFacade;
import com.vntana.core.rest.facade.client.component.precondition.ClientOrganizationServiceFacadePreconditionCheckerComponent;
import com.vntana.core.service.client.OrganizationClientService;
import com.vntana.core.service.client.dto.CreateClientOrganizationDto;
import com.vntana.core.service.client.dto.UpdateClientOrganizationDto;
import com.vntana.core.service.client.mediator.ClientOrganizationLifecycleMediator;
import com.vntana.core.service.common.component.SlugValidationComponent;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.organization.dto.GetAllOrganizationDto;
import com.vntana.core.service.user.UserService;
import com.vntana.core.service.user.role.UserRoleService;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 3:04 PM
 */
@Component
public class ClientOrganizationServiceFacadeImpl implements ClientOrganizationServiceFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientOrganizationServiceFacadeImpl.class);

    private final MapperFacade mapperFacade;
    private final OrganizationClientService organizationClientService;
    private final OrganizationService organizationService;
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final SlugValidationComponent slugValidationComponent;
    private final ClientOrganizationServiceFacadePreconditionCheckerComponent preconditionCheckerComponent;
    private final ClientOrganizationLifecycleMediator clientOrganizationLifecycleMediator;

    public ClientOrganizationServiceFacadeImpl(final MapperFacade mapperFacade,
                                               final OrganizationClientService organizationClientService,
                                               final OrganizationService organizationService,
                                               final UserService userService,
                                               final UserRoleService userRoleService,
                                               final SlugValidationComponent slugValidationComponent,
                                               final ClientOrganizationServiceFacadePreconditionCheckerComponent preconditionCheckerComponent,
                                               final ClientOrganizationLifecycleMediator clientOrganizationLifecycleMediator) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.mapperFacade = mapperFacade;
        this.organizationClientService = organizationClientService;
        this.organizationService = organizationService;
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.slugValidationComponent = slugValidationComponent;
        this.preconditionCheckerComponent = preconditionCheckerComponent;
        this.clientOrganizationLifecycleMediator = clientOrganizationLifecycleMediator;
    }

    @Override
    public CheckAvailableClientOrganizationSlugResultResponse checkSlugAvailability(final CheckAvailableClientOrganizationSlugRequest request) {
        if (StringUtils.isBlank(request.getOrganizationUuid())) {
            return new CheckAvailableClientOrganizationSlugResultResponse(HttpStatus.SC_UNPROCESSABLE_ENTITY, ClientOrganizationErrorResponseModel.MISSING_ORGANIZATION_UUID);
        }
        final SingleErrorWithStatus<ClientOrganizationErrorResponseModel> errorResponse = validateSlugErrors(request.getSlug());
        if (errorResponse.isPresent()) {
            return new CheckAvailableClientOrganizationSlugResultResponse(errorResponse.getHttpStatus(), errorResponse.getError());
        }
        final Mutable<String> mutableSlug = new MutableObject<>(request.getSlug());
        final MutableInt mutableInt = new MutableInt(1);
        while (organizationClientService.findBySlugAndOrganization(mutableSlug.getValue(), request.getOrganizationUuid()).isPresent()) {
            LOGGER.debug("Client organization with a slug - {} and organization - {} already exists, trying to generate suggested one",
                    mutableSlug.getValue(), request.getOrganizationUuid());
            mutableSlug.setValue(format("%s%d", request.getSlug(), mutableInt.getAndIncrement()));
        }
        return new CheckAvailableClientOrganizationSlugResultResponse(
                new CheckAvailableClientOrganizationSlugResponseModel(
                        mutableSlug.getValue().equals(request.getSlug()), mutableSlug.getValue()
                )
        );
    }

    @Override
    public CreateClientOrganizationResultResponse create(final CreateClientOrganizationRequest request) {
        if (StringUtils.isBlank(request.getOrganizationUuid())) {
            return new CreateClientOrganizationResultResponse(HttpStatus.SC_UNPROCESSABLE_ENTITY, ClientOrganizationErrorResponseModel.MISSING_ORGANIZATION_UUID);
        }
        final SingleErrorWithStatus<ClientOrganizationErrorResponseModel> errorResponse = validateSlugErrors(request.getSlug());
        if (errorResponse.isPresent()) {
            return new CreateClientOrganizationResultResponse(errorResponse.getHttpStatus(), errorResponse.getError());
        }
        return organizationClientService.findBySlugAndOrganization(request.getSlug(), request.getOrganizationUuid())
                .map(clientOrganization -> {
                    LOGGER.debug("Client organization already exists for a slug - {}", request.getSlug());
                    return new CreateClientOrganizationResultResponse(HttpStatus.SC_UNPROCESSABLE_ENTITY, ClientOrganizationErrorResponseModel.SLUG_ALREADY_EXISTS);
                })
                .orElseGet(() -> {
                    LOGGER.debug("Creating client organization for request - {}", request);
                    final CreateClientOrganizationDto dto = mapperFacade.map(request, CreateClientOrganizationDto.class);
                    final ClientOrganization clientOrganization = organizationClientService.create(dto);
                    clientOrganizationLifecycleMediator.onCreated(clientOrganization);
                    return new CreateClientOrganizationResultResponse(clientOrganization.getUuid());
                });
    }

    @Transactional(readOnly = true)
    @Override
    public UserClientOrganizationResponse getUserClientOrganizations(final String userUuid, final String userOrganizationUuid) {
        LOGGER.debug("Retrieving user organization's client organizations by user uuid - {} and by user organization uuid - {}", userUuid, userOrganizationUuid);
        final SingleErrorWithStatus<ClientOrganizationErrorResponseModel> errors = preconditionCheckerComponent.checkGetUserClientOrganizations(userUuid, userOrganizationUuid);
        if (errors.isPresent()) {
            return new UserClientOrganizationResponse(errors.getHttpStatus(), errors.getError());
        }
        final User user = userService.getByUuid(userUuid);
        final Organization organization = organizationService.getByUuid(userOrganizationUuid);
        final UserClientOrganizationResponse response = new UserClientOrganizationResponse(getUserClientOrganizationResponse(user, organization));
        LOGGER.debug("Successfully retrieved user organization's client organizations by user uuid - {} and by user organization uuid - {}", userUuid, userOrganizationUuid);
        return response;
    }

    @Transactional(readOnly = true)
    @Override
    public GetClientOrganizationResultResponse getByUuid(final String uuid) {
        if (StringUtils.isBlank(uuid)) {
            return new GetClientOrganizationResultResponse(HttpStatus.SC_UNPROCESSABLE_ENTITY, ClientOrganizationErrorResponseModel.MISSING_UUID);
        }
        LOGGER.debug("Retrieving client organization by uuid - {}", uuid);
        final Optional<ClientOrganization> clientOptional = organizationClientService.findByUuid(uuid);
        if (!clientOptional.isPresent()) {
            return new GetClientOrganizationResultResponse(HttpStatus.SC_NOT_FOUND, ClientOrganizationErrorResponseModel.CLIENT_NOT_FOUND);
        }
        final ClientOrganization client = clientOptional.get();
        LOGGER.debug("Successfully retrieved client organization with result - {}", client);
        final GetClientOrganizationResponseModel response = buildGetClientOrganizationResponseModel(client);
        return new GetClientOrganizationResultResponse(response);
    }

    @Transactional(readOnly = true)
    @Override
    public GetClientOrganizationBySlugResultResponse getBySlug(final String organizationUuid, final String slug) {
        if (StringUtils.isBlank(organizationUuid)) {
            return new GetClientOrganizationBySlugResultResponse(HttpStatus.SC_UNPROCESSABLE_ENTITY, ClientOrganizationErrorResponseModel.MISSING_ORGANIZATION_UUID);
        }
        if (StringUtils.isBlank(slug)) {
            return new GetClientOrganizationBySlugResultResponse(HttpStatus.SC_UNPROCESSABLE_ENTITY, ClientOrganizationErrorResponseModel.MISSING_SLUG);
        }
        LOGGER.debug("Retrieving client organization by organizationUuid - {} and slug - {}", organizationUuid, slug);
        return organizationClientService.findBySlugAndOrganization(slug, organizationUuid)
                .map(client -> {
                    LOGGER.debug("Successfully retrieved client organization with result - {}", client);
                    return new GetClientOrganizationBySlugResultResponse(buildGetClientOrganizationResponseModel(client));
                })
                .orElseGet(() -> new GetClientOrganizationBySlugResultResponse(HttpStatus.SC_NOT_FOUND, ClientOrganizationErrorResponseModel.CLIENT_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    @Override
    public GetAllOrganizationsResultResponse getAll() {
        final Page<Organization> all = organizationService
                .getAll(new GetAllOrganizationDto(organizationService.count().intValue()));
        final List<GetAllOrganizationsResponseModel> responseModels = all.stream()
                .flatMap(organization -> organization.getClientOrganizations().stream())
                .map(clientOrganization -> new GetAllOrganizationsResponseModel(
                        clientOrganization.getOrganization().getUuid(),
                        clientOrganization.getOrganization().getName(),
                        clientOrganization.getUuid(),
                        clientOrganization.getName()
                ))
                .collect(Collectors.toList());
        return new GetAllOrganizationsResultResponse(responseModels.size(), responseModels);
    }

    @Transactional
    @Override
    public UpdateClientOrganizationResultResponse update(final UpdateClientOrganizationRequest request) {
        LOGGER.debug("Updating client organization for request - {}", request);
        final UpdateClientOrganizationDto dto = mapperFacade.map(request, UpdateClientOrganizationDto.class);
        final ClientOrganization clientOrganization = organizationClientService.update(dto);
        LOGGER.debug("Successfully updated client organization for request - {}", request);
        return new UpdateClientOrganizationResultResponse(clientOrganization.getUuid());
    }

    @Transactional(readOnly = true)
    @Override
    public UserClientBulkOrganizationResponse getByUserAndBulkOrganizations(final String userUuid, final List<String> organizationsUuids) {
        LOGGER.debug("Retrieving user organization's client organizations by user uuid - {} and by user organizationsUuids - {}", userUuid, organizationsUuids);
        final SingleErrorWithStatus<ClientOrganizationErrorResponseModel> errors = preconditionCheckerComponent.checkGetByUserAndBulkOrganizations(userUuid, organizationsUuids);
        if (errors.isPresent()) {
            return new UserClientBulkOrganizationResponse(errors.getHttpStatus(), errors.getError());
        }
        final User user = userService.getByUuid(userUuid);
        final List<GetUserClientOrganizationsResponseModel> clientsResponse = organizationsUuids.stream()
                .flatMap(uuid -> {
                    final Organization organization = organizationService.getByUuid(uuid);
                    return getUserClientOrganizationResponse(user, organization).stream();
                })
                .collect(Collectors.toList());
        LOGGER.debug("Successfully retrieved user organization's client organizations by user uuid - {} and by user organizationsUuids - {}", userUuid, organizationsUuids);
        return new UserClientBulkOrganizationResponse(new GetUserClientBulkOrganizationsGridResponseModel(clientsResponse.size(), clientsResponse));
    }

    private SingleErrorWithStatus<ClientOrganizationErrorResponseModel> validateSlugErrors(final String slug) {
        LOGGER.debug("Validating client slug for slug - {}", slug);
        if (!slugValidationComponent.validate(slug)) {
            return SingleErrorWithStatus.of(HttpStatus.SC_UNPROCESSABLE_ENTITY, ClientOrganizationErrorResponseModel.SLUG_NOT_VALID);
        }
        return SingleErrorWithStatus.empty();
    }

    private List<GetUserClientOrganizationsResponseModel> getClientsForSuperAdmin(
            final User user,
            final Organization organization) {
        return user.roleOfSuperAdmin()
                .map(theRole -> organization.getClientOrganizations().stream()
                        .map(theClientOrganization -> buildGetUserClientOrganizationsResponseModel(theClientOrganization, theRole.getUserRole()))
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new IllegalStateException(format("Super Admin can't find the clients for organization - %s", organization.getUuid())));
    }

    private List<GetUserClientOrganizationsResponseModel> getClientsForAccessibleUser(final String userUuid, final String organizationUuid) {
        return userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(organizationUuid, userUuid)
                .stream()
                .map(theClientRole -> buildGetUserClientOrganizationsResponseModel(theClientRole.getClientOrganization(), theClientRole.getUserRole()))
                .collect(Collectors.toList());
    }

    private List<GetUserClientOrganizationsResponseModel> getClientsForOrganizationOwnerAndAdmin(final User user, final Organization organization) {
        return user.roles().stream().filter(role -> role instanceof UserOrganizationAdminRole
                || role instanceof UserOrganizationOwnerRole)
                .findAny()
                .map(theRole -> organization.getClientOrganizations().stream()
                        .map(theClientOrganization -> buildGetUserClientOrganizationsResponseModel(theClientOrganization, theRole.getUserRole()))
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new UnsupportedOperationException("Unsupported user organization role, should be handled during next sprints"));
    }

    private GetUserClientOrganizationsResponseModel buildGetUserClientOrganizationsResponseModel(final ClientOrganization clientOrganization, final UserRole role) {
        return new GetUserClientOrganizationsResponseModel(
                clientOrganization.getUuid(),
                clientOrganization.getSlug(),
                clientOrganization.getName(),
                clientOrganization.getImageBlobId(),
                UserRoleModel.valueOf(role.name()),
                clientOrganization.getCreated()
        );
    }

    private List<GetUserClientOrganizationsResponseModel> getUserClientOrganizationResponse(final User user, final Organization organization) {
        if (user.roleOfSuperAdmin().isPresent()) {
            return getClientsForSuperAdmin(user, organization);
        } else if (user.roleOfOrganizationOwner(organization).isPresent() || user.roleOfOrganizationAdmin(organization).isPresent()) {
            return getClientsForOrganizationOwnerAndAdmin(user, organization);
        } else {
            return getClientsForAccessibleUser(user.getUuid(), organization.getUuid());
        }
    }

    private GetClientOrganizationResponseModel buildGetClientOrganizationResponseModel(final ClientOrganization client) {
        return new GetClientOrganizationResponseModel(
                client.getOrganization().getUuid(),
                client.getOrganization().getSlug(),
                client.getUuid(),
                client.getSlug(),
                client.getName(),
                client.getImageBlobId(),
                client.getCreated()
        );
    }
}
