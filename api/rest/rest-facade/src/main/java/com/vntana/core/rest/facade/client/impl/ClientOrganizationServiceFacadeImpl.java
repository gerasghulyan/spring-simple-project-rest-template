package com.vntana.core.rest.facade.client.impl;

import com.vntana.core.domain.client.ClientOrganization;
import com.vntana.core.domain.user.User;
import com.vntana.core.model.auth.response.UserRoleModel;
import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel;
import com.vntana.core.model.client.request.CheckAvailableClientOrganizationSlugRequest;
import com.vntana.core.model.client.request.CreateClientOrganizationRequest;
import com.vntana.core.model.client.response.CheckAvailableClientOrganizationSlugResultResponse;
import com.vntana.core.model.client.response.CreateClientOrganizationResultResponse;
import com.vntana.core.model.user.response.UserClientOrganizationResponse;
import com.vntana.core.model.user.response.model.GetUserClientOrganizationsGridResponseModel;
import com.vntana.core.model.user.response.model.GetUserClientOrganizationsResponseModel;
import com.vntana.core.persistence.utils.PersistenceUtilityService;
import com.vntana.core.rest.facade.client.ClientOrganizationServiceFacade;
import com.vntana.core.service.client.ClientOrganizationService;
import com.vntana.core.service.client.dto.CreateClientOrganizationDto;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.user.UserService;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.mutable.MutableObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
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

    public ClientOrganizationServiceFacadeImpl(final MapperFacade mapperFacade,
                                               final PersistenceUtilityService persistenceUtilityService,
                                               final ClientOrganizationService clientOrganizationService,
                                               final OrganizationService organizationService,
                                               final UserService userService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.mapperFacade = mapperFacade;
        this.persistenceUtilityService = persistenceUtilityService;
        this.clientOrganizationService = clientOrganizationService;
        this.organizationService = organizationService;
        this.userService = userService;
    }

    @Override
    public CheckAvailableClientOrganizationSlugResultResponse checkSlugAvailability(final CheckAvailableClientOrganizationSlugRequest request) {
        Assert.hasText(request.getOrganizationUuid(), "The organizationUuid uuid should not be null");
        final Mutable<String> mutableSlug = new MutableObject<>(request.getSlug());
        final MutableInt mutableInt = new MutableInt(1);
        while (clientOrganizationService.findBySlugAndOrganization(mutableSlug.getValue(), request.getOrganizationUuid()).isPresent()) {
            LOGGER.debug("Client organization with slug - {} and organization - {} already exists, trying to generate suggested one",
                    mutableSlug.getValue(), request.getOrganizationUuid());
            mutableSlug.setValue(format("%s%d", request.getSlug(), mutableInt.getAndIncrement()));
        }
        return new CheckAvailableClientOrganizationSlugResultResponse(mutableSlug.getValue().equals(request.getSlug()), mutableSlug.getValue());
    }

    @Override
    public CreateClientOrganizationResultResponse create(final CreateClientOrganizationRequest request) {
        Assert.hasText(request.getOrganizationUuid(), "The organizationUuid uuid should not be null");
        return clientOrganizationService.findBySlugAndOrganization(request.getSlug(), request.getOrganizationUuid())
                .map(clientOrganization -> {
                    LOGGER.debug("Client organization already exists for slug - {}", request.getSlug());
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
            final User user = userService.getByUuid(userUuid);
            mutableResponse.setValue(user.roleOfOrganization(organizationService.getByUuid(userOrganizationUuid))
                    .map(userOrganizationRole -> userOrganizationRole
                            .getOrganization()
                            .getClientOrganizations()
                            .stream()
                            .map(clientOrganization -> new GetUserClientOrganizationsResponseModel(
                                    clientOrganization.getUuid(),
                                    clientOrganization.getName(),
                                    clientOrganization.getImageId(),
                                    UserRoleModel.valueOf(userOrganizationRole.getUserRole().name())
                            ))
                            .collect(Collectors.toList())
                    ).orElseThrow(() -> new UnsupportedOperationException("Unsupported user organization role, should be handled during next sprints")));
        });
        final List<GetUserClientOrganizationsResponseModel> response = mutableResponse.getValue();
        return new UserClientOrganizationResponse(new GetUserClientOrganizationsGridResponseModel(response.size(), response));
    }
}
