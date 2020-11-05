package com.vntana.core.service.user.role.impl;

import com.vntana.core.domain.client.ClientOrganization;
import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.user.*;
import com.vntana.core.persistence.user.role.UserRoleRepository;
import com.vntana.core.service.client.ClientOrganizationService;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.user.UserService;
import com.vntana.core.service.user.role.UserRoleService;
import com.vntana.core.service.user.role.dto.*;
import com.vntana.core.service.user.role.exception.UserOrganizationAdminRoleNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/7/20
 * Time: 12:48 PM
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleServiceImpl.class);

    private final UserRoleRepository userRoleRepository;
    private final UserService userService;
    private final OrganizationService organizationService;
    private final ClientOrganizationService clientOrganizationService;

    public UserRoleServiceImpl(final UserRoleRepository userRoleRepository,
                               final UserService userService,
                               final OrganizationService organizationService,
                               final ClientOrganizationService clientOrganizationService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.userRoleRepository = userRoleRepository;
        this.userService = userService;
        this.organizationService = organizationService;
        this.clientOrganizationService = clientOrganizationService;
    }

    @Transactional
    @Override
    public List<AbstractUserRole> findAllByOrganizationUuid(final String organizationUuid) {
        LOGGER.debug("Retrieving userRoles belonging to organization - {}", organizationUuid);
        assertOrganizationUuid(organizationUuid);
        final List<AbstractUserRole> userRoles = userRoleRepository.findAllByOrganizationUuid(organizationUuid);
        LOGGER.debug("Successfully userRoles users belonging to organization - {}", organizationUuid);
        return userRoles;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<AbstractUserRole> findByOrganizationAndUser(final String organizationUuid, final String userUuid) {
        LOGGER.debug("Retrieving userRoles belonging to organization - {} and user - {}", organizationUuid, userUuid);
        assertOrganizationUuid(organizationUuid);
        assertUserUuid(userUuid);
        final Optional<AbstractUserRole> userRole = userRoleRepository.findAllByOrganizationAndUser(organizationUuid, userUuid);
        LOGGER.debug("Successfully retrieved userRoles belonging to organization - {} and user - {}", organizationUuid, userUuid);
        return userRole;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByOrganizationAndUserAndRole(final String organizationUuid, final String userUuid, final UserRole userRole) {
        LOGGER.debug("Checking existence of userRole belonging to organization - {}, user - {}  with role - {}", organizationUuid, userUuid, userRole);
        assertOrganizationUuid(organizationUuid);
        assertUserUuid(userUuid);
        assertUserRole(userRole);
        final List<AbstractUserRole> roles = userRoleRepository.findAllByOrganizationUuid(organizationUuid).stream()
                .filter(abstractUserRole -> abstractUserRole.getUserRole() == userRole && abstractUserRole.getUser().getUuid().equals(userUuid))
                .collect(Collectors.toList());
        if (roles.size() > 1) {
            throw new IllegalStateException(format("More then 1 role found in organization %s for user %s and role %s", organizationUuid, userUuid, userRole));
        }
        LOGGER.debug("Successfully checked existence of userRole belonging to organization - {}, user - {}  with role - {}", organizationUuid, userUuid, userRole);
        return !CollectionUtils.isEmpty(roles);
    }

    @Transactional
    @Override
    public UserOrganizationOwnerRole grantOrganizationOwnerRole(final UserGrantOrganizationRoleDto dto) {
        LOGGER.debug("Granting owner role using dto - {}", dto);
        Assert.notNull(dto, "The GrantUserOrganizationRoleDto should not be null");
        final User user = userService.getByUuid(dto.getUserUuid());
        final Organization organization = organizationService.getByUuid(dto.getOrganizationUuid());
        final UserOrganizationOwnerRole userRole = userRoleRepository.save(new UserOrganizationOwnerRole(user, organization));
        LOGGER.debug("Successfully granted owner role using dto - {}", dto);
        return userRole;
    }

    @Transactional
    @Override
    public UserOrganizationAdminRole grantOrganizationAdminRole(final UserGrantOrganizationRoleDto dto) {
        LOGGER.debug("Granting admin role using dto - {}", dto);
        Assert.notNull(dto, "The UserGrantOrganizationRoleDto should not be null");
        final User user = userService.getByUuid(dto.getUserUuid());
        final Organization organization = organizationService.getByUuid(dto.getOrganizationUuid());
        final UserOrganizationAdminRole userRole = userRoleRepository.save(new UserOrganizationAdminRole(user, organization));
        LOGGER.debug("Successfully granted admin role using dto - {}", dto);
        return userRole;
    }

    @Transactional
    @Override
    public UserSuperAdminRole grantSuperAdminRole(final String userUuid) {
        assertUserUuid(userUuid);
        LOGGER.debug("Processing grantSuperAdminRole for user - {}", userUuid);
        final User user = userService.getByUuid(userUuid);
        UserSuperAdminRole userSuperAdminRole = new UserSuperAdminRole(user);
        final UserSuperAdminRole saveSuperAdminRole = userRoleRepository.save(userSuperAdminRole);
        LOGGER.debug("Successfully processed grantSuperAdminRole for user - {}", userUuid);
        return saveSuperAdminRole;
    }

    @Override
    public AbstractUserRole grantClientRole(final UserGrantClientRoleDto dto) {
        LOGGER.debug("Granting client role using dto - {}", dto);
        Assert.notNull(dto, "The UserGrantClientRoleDto should not be null");
        final User user = userService.getByUuid(dto.getUserUuid());
        final ClientOrganization clientOrganization = clientOrganizationService.getByUuid(dto.getClientOrganizationUuid());
        final AbstractUserRole userClientRole = buildClientRole(dto.getClientRole(), user, clientOrganization);
        final AbstractUserRole userRole = userRoleRepository.save(userClientRole);
        LOGGER.debug("Successfully granted client role using dto - {}", dto);
        return userRole;
    }

    @Transactional
    @Override
    public void revokeOrganizationAdminRole(final UserRevokeOrganizationAdminRoleDto dto) {
        LOGGER.debug("Revoking admin role using dto - {}", dto);
        Assert.notNull(dto, "The UserRevokeOrganizationAdminRoleDto should not be null");
        final Optional<AbstractUserRole> roleOptional = userRoleRepository.findAdminRoleByUserAndOrganization(
                dto.getUserUuid(),
                dto.getOrganizationUuid()
        );
        if (!roleOptional.isPresent()) {
            throw new UserOrganizationAdminRoleNotFoundException(dto.getUserUuid(), dto.getOrganizationUuid());
        }
        roleOptional.ifPresent(userRoleRepository::delete);
        LOGGER.debug("Successfully revoked admin role using dto - {}", dto);
    }

    @Override
    public void revokeClientRole(final UserRevokeClientRoleDto dto) {

    }

    private AbstractUserRole buildClientRole(final UserClientRole clientRole, final User user, final ClientOrganization clientOrganization) {
        switch (clientRole) {
            case CLIENT_ADMIN:
                return new UserClientAdminRole(user, clientOrganization);
            case CLIENT_CONTENT_MANAGER:
                return new UserClientContentManagerRole(user, clientOrganization);
            case CLIENT_VIEWER:
                return new UserClientViewerRole(user, clientOrganization);
            default:
                throw new IllegalStateException(format("Unknown user client role %s", clientRole));
        }
    }
    
    private void assertUserUuid(final String userUuid) {
        Assert.hasText(userUuid, "The userUuid should not be null or empty");
    }

    private void assertUserRole(final UserRole userRole) {
        Assert.notNull(userRole, "The userRole should not be null");
    }
    
    private void assertOrganizationUuid(final String organizationUuid) {
        Assert.hasText(organizationUuid, "The organizationUuid should not be null or empty");
    }
}
