package com.vntana.core.service.user.impl;

import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.user.User;
import com.vntana.core.persistence.user.UserRepository;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.user.UserService;
import com.vntana.core.service.user.dto.CreateUserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

import static java.lang.String.format;

/**
 * Created by Arthur Asatryan.
 * Date: 10/3/19
 * Time: 6:39 PM
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final OrganizationService organizationService;

    public UserServiceImpl(final UserRepository userRepository, final OrganizationService organizationService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.userRepository = userRepository;
        this.organizationService = organizationService;
    }

    @Transactional
    @Override
    public User create(final CreateUserDto dto) {
        assertCreateDto(dto);
        LOGGER.debug("Creating user for dto - {}", dto);
        final Organization organization = getOrganization(dto);
        final User user = new User(dto.getFullName(), dto.getEmail(), dto.getPassword());
        final User savedUser = userRepository.save(user);
        savedUser.grantOrganizationRole(organization);
        LOGGER.debug("Successfully created user for dto - {}", dto);
        return savedUser;
    }

    @Override
    public Optional<User> findByEmail(final String email) {
        Assert.notNull(email, "The user email should not be null");
        LOGGER.debug("Getting user for email - {}", email);
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByUuid(final String uuid) {
        Assert.notNull(uuid, "The user uuid should not be null");
        return userRepository.findByUuid(uuid);
    }

    private void assertCreateDto(final CreateUserDto dto) {
        Assert.notNull(dto, "The user creation dto should not be null");
        Assert.hasText(dto.getFullName(), "The user full name should not be null or empty");
        Assert.hasText(dto.getEmail(), "The user email should not be null or empty");
        Assert.hasText(dto.getPassword(), "The user password should not be null or empty");
        Assert.hasText(dto.getOrganizationUuid(), "The organization uuid should not be null or empty");
        Assert.notNull(dto.getRole(), "The user role should not be null");
    }

    private Organization getOrganization(final CreateUserDto dto) {
        return organizationService.findByUuid(dto.getOrganizationUuid()).orElseThrow(() -> {
            LOGGER.error("Can not find organization for uuid - {}", dto.getOrganizationUuid());
            return new IllegalStateException(format("Can not find organization for uuid - %s", dto.getOrganizationUuid()));
        });
    }
}
