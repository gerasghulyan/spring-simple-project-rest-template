package com.vntana.core.service.user.role.impl;

import com.vntana.core.domain.user.AbstractUserRole;
import com.vntana.core.persistence.user.role.UserRoleRepository;
import com.vntana.core.service.user.role.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/7/20
 * Time: 12:48 PM
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleServiceImpl.class);

    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(final UserRoleRepository userRoleRepository) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.userRoleRepository = userRoleRepository;
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
}
