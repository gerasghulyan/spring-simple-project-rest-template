package com.vntana.core.rest.facade.client.impl;

import com.vntana.core.domain.client.ClientOrganization;
import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.user.User;
import com.vntana.core.domain.user.UserRole;
import com.vntana.core.model.auth.response.UserRoleModel;
import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel;
import com.vntana.core.model.client.request.CheckAvailableClientOrganizationSlugRequest;
import com.vntana.core.model.client.request.CreateClientOrganizationRequest;
import com.vntana.core.model.client.request.UpdateClientOrganizationRequest;
import com.vntana.core.model.client.response.CheckAvailableClientOrganizationSlugResultResponse;
import com.vntana.core.model.client.response.CreateClientOrganizationResultResponse;
import com.vntana.core.model.client.response.UpdateClientOrganizationResultResponse;
import com.vntana.core.model.client.response.get.*;
import com.vntana.core.model.user.response.UserClientOrganizationResponse;
import com.vntana.core.model.user.response.model.GetUserClientOrganizationsGridResponseModel;
import com.vntana.core.model.user.response.model.GetUserClientOrganizationsResponseModel;
import com.vntana.core.persistence.utils.PersistenceUtilityService;
import com.vntana.core.rest.facade.client.ClientOrganizationServiceFacade;
import com.vntana.core.service.client.ClientOrganizationService;
import com.vntana.core.service.client.dto.CreateClientOrganizationDto;
import com.vntana.core.service.client.dto.UpdateClientOrganizationDto;
import com.vntana.core.service.common.component.SlugValidationComponent;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.user.UserService;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.mutable.MutableObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
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
    private final PersistenceUtilityService persistenceUtilityService;
    private final ClientOrganizationService clientOrganizationService;
    private final OrganizationService organizationService;
    private final UserService userService;
    private final SlugValidationComponent slugValidationComponent;

    public ClientOrganizationServiceFacadeImpl(final MapperFacade mapperFacade,
                                               final PersistenceUtilityService persistenceUtilityService,
                                               final ClientOrganizationService clientOrganizationService,
                                               final OrganizationService organizationService,
                                               final UserService userService,
                                               final SlugValidationComponent slugValidationComponent) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.mapperFacade = mapperFacade;
        this.persistenceUtilityService = persistenceUtilityService;
        this.clientOrganizationService = clientOrganizationService;
        this.organizationService = organizationService;
        this.userService = userService;
        this.slugValidationComponent = slugValidationComponent;
    }

    @Override
    public CheckAvailableClientOrganizationSlugResultResponse checkSlugAvailability(final CheckAvailableClientOrganizationSlugRequest request) {
        Assert.hasText(request.getOrganizationUuid(), "The organizationUuid uuid should not be null");
        final List<ClientOrganizationErrorResponseModel> possibleErrors = validateSlugErrors(request.getSlug());
        if (!possibleErrors.isEmpty()) {
            return new CheckAvailableClientOrganizationSlugResultResponse(possibleErrors);
        }
        final Mutable<String> mutableSlug = new MutableObject<>(request.getSlug());
        final MutableInt mutableInt = new MutableInt(1);
        while (clientOrganizationService.findBySlugAndOrganization(mutableSlug.getValue(), request.getOrganizationUuid()).isPresent()) {
            LOGGER.debug("Client organization with a slug - {} and organization - {} already exists, trying to generate suggested one",
                    mutableSlug.getValue(), request.getOrganizationUuid());
            mutableSlug.setValue(format("%s%d", request.getSlug(), mutableInt.getAndIncrement()));
        }
        return new CheckAvailableClientOrganizationSlugResultResponse(mutableSlug.getValue().equals(request.getSlug()), mutableSlug.getValue());
    }

    @Override
    public CreateClientOrganizationResultResponse create(final CreateClientOrganizationRequest request) {
        Assert.hasText(request.getOrganizationUuid(), "The organizationUuid uuid should not be null");
        final List<ClientOrganizationErrorResponseModel> possibleErrors = validateSlugErrors(request.getSlug());
        if (!possibleErrors.isEmpty()) {
            return new CreateClientOrganizationResultResponse(possibleErrors);
        }
        return clientOrganizationService.findBySlugAndOrganization(request.getSlug(), request.getOrganizationUuid())
                .map(clientOrganization -> {
                    LOGGER.debug("Client organization already exists for a slug - {}", request.getSlug());
                    return new CreateClientOrganizationResultResponse(Collections.singletonList(ClientOrganizationErrorResponseModel.SLUG_ALREADY_EXISTS));
                })
                .orElseGet(() -> {
                    LOGGER.debug("Creating client organization for request - {}", request);
                    final CreateClientOrganizationDto dto = mapperFacade.map(request, CreateClientOrganizationDto.class);
                    final ClientOrganization clientOrganization = clientOrganizationService.create(dto);
                    return new CreateClientOrganizationResultResponse(clientOrganization.getUuid());
                });
    }

    @Override
    public UserClientOrganizationResponse getUserClientOrganizations(final String userUuid, final String userOrganizationUuid) {
        LOGGER.debug("Retrieving user organization's client organizations by user uuid - {} and by user organization uuid - {}", userUuid, userOrganizationUuid);
        final Mutable<List<GetUserClientOrganizationsResponseModel>> mutableResponse = new MutableObject<>();
        persistenceUtilityService.runInPersistenceSession(() -> {
            final Organization organization = organizationService.getByUuid(userOrganizationUuid);
            final User user = userService.getByUuid(userUuid);
            final List<GetUserClientOrganizationsResponseModel> response;
            if (user.roleOfSuperAdmin().isPresent()) {
                response = getClientsForSuperAdmin(user, organization);
            } else {
                response = getClientsForOrganizationAdmin(user, organization);
            }
            mutableResponse.setValue(response);
        });
        final List<GetUserClientOrganizationsResponseModel> response = mutableResponse.getValue();
        return new UserClientOrganizationResponse(new GetUserClientOrganizationsGridResponseModel(response.size(), response));
    }

    @Transactional(readOnly = true)
    @Override
    public GetClientOrganizationResultResponse getByUuid(final String uuid) {
        if (StringUtils.isBlank(uuid)) {
            return new GetClientOrganizationResultResponse(Collections.singletonList(ClientOrganizationErrorResponseModel.MISSING_UUID));
        }
        LOGGER.debug("Retrieving client organization by uuid - {}", uuid);
        final ClientOrganization client = clientOrganizationService.getByUuid(uuid);
        LOGGER.debug("Successfully retrieved client organization with result - {}", client);
        final GetClientOrganizationResponseModel response = new GetClientOrganizationResponseModel(
                client.getOrganization().getUuid(),
                client.getOrganization().getSlug(),
                client.getUuid(),
                client.getSlug(),
                client.getName(),
                client.getImageId(),
                client.getCreated()
        );
        return new GetClientOrganizationResultResponse(response);
    }

    @Transactional(readOnly = true)
    @Override
    public GetClientOrganizationBySlugResultResponse getBySlug(final String organizationUuid, final String slug) {
        if (StringUtils.isBlank(organizationUuid)) {
            return new GetClientOrganizationBySlugResultResponse(Collections.singletonList(ClientOrganizationErrorResponseModel.MISSING_ORGANIZATION_UUID));
        }
        if (StringUtils.isBlank(slug)) {
            return new GetClientOrganizationBySlugResultResponse(Collections.singletonList(ClientOrganizationErrorResponseModel.MISSING_SLUG));
        }
        LOGGER.debug("Retrieving client organization by organizationUuid - {} and slug - {}", organizationUuid, slug);
        return clientOrganizationService.findBySlugAndOrganization(slug, organizationUuid)
                .map(client -> {
                    LOGGER.debug("Successfully retrieved client organization with result - {}", client);
                    return new GetClientOrganizationBySlugResultResponse(
                            new GetClientOrganizationResponseModel(
                                    client.getOrganization().getUuid(),
                                    client.getOrganization().getSlug(),
                                    client.getUuid(),
                                    client.getSlug(),
                                    client.getName(),
                                    client.getImageId(),
                                    client.getCreated()
                            ));
                })
                .orElseGet(() -> new GetClientOrganizationBySlugResultResponse(Collections.singletonList(ClientOrganizationErrorResponseModel.CLIENT_NOT_FOUND)));
    }

    @Transactional(readOnly = true)
    @Override
    public GetAllOrganizationsResultResponse getAll() {
        final List<GetAllOrganizationsResponseModel> responseModels = organizationService.getAll().stream()
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
        final ClientOrganization clientOrganization = clientOrganizationService.update(dto);
        LOGGER.debug("Successfully updated client organization for request - {}", request);
        return new UpdateClientOrganizationResultResponse(clientOrganization.getUuid());
    }

    private List<ClientOrganizationErrorResponseModel> validateSlugErrors(final String slug) {
        LOGGER.debug("Validating client slug for slug - {}", slug);
        if (!slugValidationComponent.validate(slug)) {
            return Collections.singletonList(ClientOrganizationErrorResponseModel.SLUG_NOT_VALID);
        }
        return Collections.emptyList();
    }

    private List<GetUserClientOrganizationsResponseModel> getClientsForSuperAdmin(
            final User user,
            final Organization organization) {
        return user.roleOfSuperAdmin()
                .map(role -> buildClients(organization, role.getUserRole()))
                .orElseThrow(() -> new IllegalStateException(format("Super Admin can't find the clients for organization - %s", organization.getUuid())));
    }

    private List<GetUserClientOrganizationsResponseModel> getClientsForOrganizationAdmin(
            final User user,
            final Organization organization) {
        return user.roleOfOrganization(organization)
                .map(role -> buildClients(organization, role.getUserRole()))
                .orElseThrow(() -> new UnsupportedOperationException("Unsupported user organization role, should be handled during next sprints"));
    }

    private List<GetUserClientOrganizationsResponseModel> buildClients(final Organization organization, final UserRole role) {
        return organization.getClientOrganizations()
                .stream()
                .map(clientOrganization -> new GetUserClientOrganizationsResponseModel(
                        clientOrganization.getUuid(),
                        clientOrganization.getSlug(),
                        clientOrganization.getName(),
                        clientOrganization.getImageId(),
                        UserRoleModel.valueOf(role.name()),
                        clientOrganization.getCreated()
                ))
                .collect(Collectors.toList());
    }
}
