package com.vntana.core.service.user.role.impl;

import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.user.*;
import com.vntana.core.persistence.user.role.UserRoleRepository;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.user.UserService;
import com.vntana.core.service.user.role.UserRoleService;
import com.vntana.core.service.user.role.dto.UserGrantOrganizationRoleDto;
import com.vntana.core.service.user.role.dto.UserRevokeOrganizationAdminRoleDto;
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

    public UserRoleServiceImpl(final UserRoleRepository userRoleRepository,
                               final UserService userService,
                               final OrganizationService organizationService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.userRoleRepository = userRoleRepository;
        this.userService = userService;
        this.organizationService = organizationService;
    }

    @Transactional
    @Override
    public List<AbstractUserRole> findAllByOrganizationUuid(final String organizationUuid) {
        LOGGER.debug("Retrieving userRoles belonging to organization - {}", organizationUuid);
        Assert.hasText(organizationUuid, "The organizationUuid should not be null or empty");
        final List<AbstractUserRole> userRoles = userRoleRepository.findAllByOrganizationUuid(organizationUuid);
        LOGGER.debug("Successfully userRoles users belonging to organization - {}", organizationUuid);
        return userRoles;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByOrganizationAndUserAndRole(final String organizationUuid, final String userUuid, final UserRole userRole) {
        LOGGER.debug("Checking existence of userRole belonging to organization - {}, user - {}  with role - {}", organizationUuid, userUuid, userRole);
        Assert.hasText(organizationUuid, "The organizationUuid should not be null or empty");
        Assert.notNull(userRole, "The userRole should not be null");
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
        Assert.notNull(dto, "The GrantUserOrganizationRoleDto should not be null");
        final User user = userService.getByUuid(dto.getUserUuid());
        final Organization organization = organizationService.getByUuid(dto.getOrganizationUuid());
        final UserOrganizationAdminRole userRole = userRoleRepository.save(new UserOrganizationAdminRole(user, organization));
        LOGGER.debug("Successfully granted admin role using dto - {}", dto);
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
}
