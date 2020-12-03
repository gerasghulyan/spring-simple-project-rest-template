package com.vntana.core.service.user.role.impl;

import com.vntana.core.domain.client.ClientOrganization;
import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.user.*;
import com.vntana.core.persistence.user.role.UserRoleRepository;
import com.vntana.core.service.client.OrganizationClientService;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.user.UserService;
import com.vntana.core.service.user.role.UserRoleService;
import com.vntana.core.service.user.role.dto.*;
import com.vntana.core.service.user.role.exception.UserClientRoleNotFoundException;
import com.vntana.core.service.user.role.exception.UserClientsIncorrectRolesRevokeException;
import com.vntana.core.service.user.role.exception.UserOrganizationAdminRoleNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
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
    private final OrganizationClientService clientOrganizationService;

    public UserRoleServiceImpl(final UserRoleRepository userRoleRepository,
                               final UserService userService,
                               final OrganizationService organizationService,
                               final OrganizationClientService clientOrganizationService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.userRoleRepository = userRoleRepository;
        this.userService = userService;
        this.organizationService = organizationService;
        this.clientOrganizationService = clientOrganizationService;
    }

    @Transactional(readOnly = true)
    @Override
    public List<AbstractOrganizationAwareUserRole> findAllByOrganization(final String organizationUuid) {
        LOGGER.debug("Retrieving userRoles belonging to organization - {}", organizationUuid);
        assertOrganizationUuid(organizationUuid);
        final List<AbstractOrganizationAwareUserRole> userRoles = userRoleRepository.findAllByOrganization(organizationUuid)
                .stream()
                .map(AbstractOrganizationAwareUserRole.class::cast)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
        LOGGER.debug("Successfully userRoles users belonging to organization - {}", organizationUuid);
        return userRoles;
    }

    @Transactional(readOnly = true)
    @Override
    public List<AbstractClientOrganizationAwareUserRole> findAllByClientOrganization(final String clientOrganizationUuid) {
        LOGGER.debug("Retrieving userRoles belonging to client organization - {}", clientOrganizationUuid);
        Assert.hasText(clientOrganizationUuid, "The client organization uuid should not be null or empty");
        final List<AbstractClientOrganizationAwareUserRole> userRoles = userRoleRepository.findAllByClientOrganization(clientOrganizationUuid)
                .stream()
                .map(AbstractClientOrganizationAwareUserRole.class::cast)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
        LOGGER.debug("Successfully userRoles users belonging to client organization - {}", clientOrganizationUuid);
        return userRoles;
    }

    @Transactional(readOnly = true)
    @Override
    public List<AbstractClientOrganizationAwareUserRole> findAllClientsByOrganization(final String organizationUuid) {
        LOGGER.debug("Retrieving user client roles belonging to organization - {}", organizationUuid);
        assertOrganizationUuid(organizationUuid);
        final List<AbstractClientOrganizationAwareUserRole> userRoles = userRoleRepository.findAllOrganizationClientsByOrganization(organizationUuid)
                .stream()
                .map(AbstractClientOrganizationAwareUserRole.class::cast)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
        LOGGER.debug("Successfully user client roles belonging to organization - {}", organizationUuid);
        return userRoles;
    }

    @Transactional(readOnly = true)
    @Override
    public List<AbstractClientOrganizationAwareUserRole> findAllClientOrganizationRoleByOrganizationAndUser(final String organizationUuid, final String userUuid) {
        LOGGER.debug("Retrieving user client role belonging to organization - {}, user - {}", organizationUuid, userUuid);
        Assert.hasText(organizationUuid, "The organizationUuid should not be null or empty");
        assertUserUuid(userUuid);
        final List<AbstractClientOrganizationAwareUserRole> roles = userRoleRepository.findAllOrganizationClientsRolesByOrganizationAndUser(organizationUuid, userUuid).stream()
                .map(AbstractClientOrganizationAwareUserRole.class::cast)
                .collect(Collectors.toList());
        LOGGER.debug("Successfully retrieved user client role belonging to organization - {}, user - {}", organizationUuid, userUuid);
        return roles;
    }


    @Transactional(readOnly = true)
    @Override
    public Optional<AbstractOrganizationAwareUserRole> findByOrganizationAndUser(final String organizationUuid, final String userUuid) {
        LOGGER.debug("Retrieving userRoles belonging to organization - {} and user - {}", organizationUuid, userUuid);
        assertOrganizationUuid(organizationUuid);
        assertUserUuid(userUuid);
        final Optional<AbstractOrganizationAwareUserRole> userRole = userRoleRepository.findByOrganizationAndUser(organizationUuid, userUuid)
                .map(AbstractOrganizationAwareUserRole.class::cast);
        LOGGER.debug("Successfully retrieved userRoles belonging to organization - {} and user - {}", organizationUuid, userUuid);
        return userRole;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<AbstractClientOrganizationAwareUserRole> findByClientOrganizationAndUser(final String clientOrganizationUuid, final String userUuid) {
        LOGGER.debug("Retrieving userRole belonging to client organization - {} and user - {}", clientOrganizationUuid, userUuid);
        assertOrganizationUuid(clientOrganizationUuid);
        assertUserUuid(userUuid);
        final Optional<AbstractClientOrganizationAwareUserRole> clientUserRole = userRoleRepository.findByClientOrganizationAndUser(clientOrganizationUuid, userUuid)
                .map(AbstractClientOrganizationAwareUserRole.class::cast);
        LOGGER.debug("Successfully retrieved userRole belonging to client organization - {} and user - {}", clientOrganizationUuid, userUuid);
        return clientUserRole;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByOrganizationAndUserAndRole(final String organizationUuid, final String userUuid, final UserRole userRole) {
        LOGGER.debug("Checking existence of userRole belonging to organization - {}, user - {}  with role - {}", organizationUuid, userUuid, userRole);
        assertOrganizationUuid(organizationUuid);
        assertUserUuid(userUuid);
        assertOrganizationRelatedUserRole(userRole);
        final List<AbstractUserRole> roles = userRoleRepository.findAllByOrganization(organizationUuid).stream()
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

    @Transactional
    @Override
    public AbstractClientOrganizationAwareUserRole grantClientRole(final UserGrantClientRoleDto dto) {
        LOGGER.debug("Granting client role using dto - {}", dto);
        Assert.notNull(dto, "The UserGrantClientRoleDto should not be null");
        final User user = userService.getByUuid(dto.getUserUuid());
        final ClientOrganization clientOrganization = clientOrganizationService.getByUuid(dto.getClientOrganizationUuid());
        final AbstractClientOrganizationAwareUserRole userClientRole = user.buildClientRole(dto.getClientRole(), clientOrganization);
        final AbstractClientOrganizationAwareUserRole userRole = userRoleRepository.save(userClientRole);
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

    @Transactional
    @Override
    public void revokeClientRole(final UserRevokeClientRoleDto dto) {
        LOGGER.debug("Revoking client role using dto - {}", dto);
        Assert.notNull(dto, "The UserRevokeClientRoleDto should not be null");
        final Optional<AbstractUserRole> roleOptional = findClientRoleByUserAndOrganization(dto);
        if (!roleOptional.isPresent()) {
            throw new UserClientRoleNotFoundException(dto.getClientRole(), dto.getUserUuid(), dto.getClientOrganizationUuid());
        }
        roleOptional.ifPresent(userRoleRepository::delete);
        LOGGER.debug("Successfully revoked client role using dto - {}", dto);
    }

    @Transactional
    @Override
    public void revokeUserClientsRoles(final UserRevokeClientsRolesDto dto) {
        LOGGER.debug("Revoking clients roles using dto - {}", dto);
        Assert.notNull(dto, "The UserRevokeClientsRolesDto should not be null");
        final int deletedRoles = userRoleRepository.deleteAllForUserAndClientOrganizations(dto.getUserUuid(), dto.getClientUuids());
        if (deletedRoles != dto.getClientUuids().size()) {
            throw new UserClientsIncorrectRolesRevokeException(dto.getUserUuid(), dto.getClientUuids());
        }
        LOGGER.debug("Successfully revoked clients roles using dto - {}", dto);
    }

    private Optional<AbstractUserRole> findClientRoleByUserAndOrganization(final UserRevokeClientRoleDto dto) {
        switch (dto.getClientRole()) {
            case CLIENT_ORGANIZATION_ADMIN:
                return userRoleRepository.findClientAdminRoleByUserAndClientOrganization(
                        dto.getUserUuid(),
                        dto.getClientOrganizationUuid()
                );
            case CLIENT_ORGANIZATION_CONTENT_MANAGER:
                return userRoleRepository.findClientContentManagerRoleByUserAndClientOrganization(
                        dto.getUserUuid(),
                        dto.getClientOrganizationUuid()
                );
            case CLIENT_ORGANIZATION_VIEWER:
                return userRoleRepository.findClientViewerRoleByUserAndClientOrganization(
                        dto.getUserUuid(),
                        dto.getClientOrganizationUuid()
                );
            default:
                throw new IllegalStateException(format("Unknown user client role %s", dto.getClientRole()));
        }
    }

    private void assertUserUuid(final String userUuid) {
        Assert.hasText(userUuid, "The userUuid should not be null or empty");
    }

    private void assertOrganizationRelatedUserRole(final UserRole userRole) {
        Assert.notNull(userRole, "The userRole should not be null");
        Assert.isTrue(userRole.equals(UserRole.ORGANIZATION_OWNER) || userRole.equals(UserRole.ORGANIZATION_ADMIN), "The should be organization related user roles");
    }

    private void assertOrganizationUuid(final String organizationUuid) {
        Assert.hasText(organizationUuid, "The organizationUuid should not be null or empty");
    }
}
