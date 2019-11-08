package com.vntana.core.rest.facade.organization.impl;

import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.user.User;
import com.vntana.core.domain.user.UserClientOrganizationRole;
import com.vntana.core.domain.user.UserOrganizationRole;
import com.vntana.core.model.auth.response.UserRoleModel;
import com.vntana.core.model.organization.error.OrganizationErrorResponseModel;
import com.vntana.core.model.organization.request.CheckAvailableOrganizationSlugRequest;
import com.vntana.core.model.organization.request.CreateOrganizationRequest;
import com.vntana.core.model.organization.response.CheckAvailableOrganizationSlugResultResponse;
import com.vntana.core.model.organization.response.CreateOrganizationResultResponse;
import com.vntana.core.model.user.response.model.GetUserOrganizationsResultResponseModel;
import com.vntana.core.model.user.response.model.UserOrganizationResultResponse;
import com.vntana.core.persistence.utils.PersistenceUtilityService;
import com.vntana.core.rest.facade.organization.OrganizationServiceFacade;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.organization.dto.CreateOrganizationDto;
import com.vntana.core.service.user.UserService;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.mutable.MutableObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
public class OrganizationServiceFacadeImpl implements OrganizationServiceFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationServiceFacadeImpl.class);

    private final MapperFacade mapperFacade;
    private final OrganizationService organizationService;
    private final UserService userService;
    private final PersistenceUtilityService persistenceUtilityService;

    public OrganizationServiceFacadeImpl(
            final MapperFacade mapperFacade,
            final OrganizationService organizationService,
            final UserService userService,
            final PersistenceUtilityService persistenceUtilityService) {
        this.userService = userService;
        this.mapperFacade = mapperFacade;
        this.organizationService = organizationService;
        this.persistenceUtilityService = persistenceUtilityService;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Override
    public CheckAvailableOrganizationSlugResultResponse checkSlugAvailability(final CheckAvailableOrganizationSlugRequest request) {
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
        return organizationService.findBySlug(request.getSlug())
                .map(organization -> {
                    LOGGER.debug("Organization already exists for slug - {}", request.getSlug());
                    return new CreateOrganizationResultResponse(Collections.singletonList(OrganizationErrorResponseModel.SLUG_ALREADY_EXISTS));
                })
                .orElseGet(() -> {
                    LOGGER.debug("Creating organization for request - {}", request);
                    final CreateOrganizationDto dto = mapperFacade.map(request, CreateOrganizationDto.class);
                    final Organization organization = organizationService.create(dto);
                    return new CreateOrganizationResultResponse(organization.getUuid());
                });
    }

    @Override
    public UserOrganizationResultResponse getUserOrganizations(final String uuid) {
        final Mutable<List<GetUserOrganizationsResultResponseModel>> mutableResponse = new MutableObject<>();
        persistenceUtilityService.runInNewTransaction(() -> {
            final User user = userService.getByUuid(uuid);
            final List<GetUserOrganizationsResultResponseModel> response = user.roleOfSuperAdmin()
                    .map(userSuperAdminRole -> getOrganizationsWhenAdmin())
                    .orElseGet(() -> getOrganizationsWhenNotAdmin(user));
            mutableResponse.setValue(response);
        });
        final List<GetUserOrganizationsResultResponseModel> response = mutableResponse.getValue();
        return new UserOrganizationResultResponse(response.size(), response);
    }

    private List<GetUserOrganizationsResultResponseModel> getOrganizationsWhenNotAdmin(final User user) {
        return user.roles()
                .stream()
                .map(userRole -> {
                    switch (userRole.getType()) {
                        case ORGANIZATION_ROLE:
                            final UserOrganizationRole userOrganizationRole = (UserOrganizationRole) userRole;
                            return new GetUserOrganizationsResultResponseModel(
                                    userOrganizationRole.getOrganization().getUuid(),
                                    userOrganizationRole.getOrganization().getName(),
                                    UserRoleModel.valueOf(userOrganizationRole.getUserRole().name())
                            );
                        case CLIENT_ROLE:
                            final UserClientOrganizationRole userClientOrganizationRole = (UserClientOrganizationRole) userRole;
                            return new GetUserOrganizationsResultResponseModel(
                                    userClientOrganizationRole.getClientOrganization().getOrganization().getUuid(),
                                    userClientOrganizationRole.getClientOrganization().getOrganization().getName(),
                                    UserRoleModel.valueOf(userClientOrganizationRole.getUserRole().name())
                            );
                        default:
                            throw new IllegalStateException(format("Unknown user role %s", userRole.toString()));
                    }
                })
                .collect(Collectors.toList());
    }

    private List<GetUserOrganizationsResultResponseModel> getOrganizationsWhenAdmin() {
        return organizationService.getAll().stream()
                .map(organization -> new GetUserOrganizationsResultResponseModel(
                        organization.getUuid(),
                        organization.getName(),
                        UserRoleModel.SUPER_ADMIN
                ))
                .collect(Collectors.toList());
    }
}
