package com.vntana.core.rest.facade.user.impl;


import com.vntana.core.domain.client.ClientOrganization;
import com.vntana.core.domain.user.User;
import com.vntana.core.domain.user.UserRole;
import com.vntana.core.model.user.request.UserCreateRequest;
import com.vntana.core.model.user.response.UserCreateResponseModel;
import com.vntana.core.model.user.response.UserCreateResultResponse;
import com.vntana.core.persistence.utils.PersistenceUtilityService;
import com.vntana.core.rest.facade.user.UserServiceFacade;
import com.vntana.core.service.client.ClientOrganizationService;
import com.vntana.core.service.client.dto.CreateClientOrganizationDto;
import com.vntana.core.service.user.UserService;
import com.vntana.core.service.user.dto.UserCreateDto;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by Arthur Asatryan.
 * Date: 10/1/19
 * Time: 6:35 PM
 */
@Component
public class UserServiceFacadeImpl implements UserServiceFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceFacadeImpl.class);

    private final UserService userService;
    private final MapperFacade mapperFacade;
    private final ClientOrganizationService clientOrganizationService;
    private final PersistenceUtilityService persistenceUtilityService;

    public UserServiceFacadeImpl(final UserService userService,
                                 final MapperFacade mapperFacade,
                                 final ClientOrganizationService clientOrganizationService,
                                 final PersistenceUtilityService persistenceUtilityService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.userService = userService;
        this.mapperFacade = mapperFacade;
        this.clientOrganizationService = clientOrganizationService;
        this.persistenceUtilityService = persistenceUtilityService;
    }

    @Override
    public UserCreateResultResponse create(final UserCreateRequest request) {
        LOGGER.debug("Processing Facade createUser method for request - {}", request);
        Assert.notNull(request, "The USerCreateRequest should not be null");
        final Mutable<String> mutableUserUuid = new MutableObject<>();
        persistenceUtilityService.runInNewTransaction(() -> {
            LOGGER.debug("Creating a client organization for request - {}", request);
            final ClientOrganization clientOrganization = clientOrganizationService.create(
                    new CreateClientOrganizationDto(request.getClientName(), request.getClientSlug(), request.getOrganizationUuid())
            );
            final User user = userService.create(new UserCreateDto(
                    request.getFullName(),
                    request.getEmail(),
                    request.getPassword(),
                    clientOrganization.getUuid(),
                    UserRole.ORGANIZATION_ADMIN
            ));
            mutableUserUuid.setValue(user.getUuid());
            LOGGER.debug("Successfully created user - {} for request - {}", user, request);
        });
        return new UserCreateResultResponse(new UserCreateResponseModel(mutableUserUuid.getValue()));
    }
}
