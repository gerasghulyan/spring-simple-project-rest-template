package com.vntana.core.service.user.impl;

import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.user.User;
import com.vntana.core.persistence.user.UserRepository;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.user.UserService;
import com.vntana.core.service.user.dto.CreateUserDto;
import com.vntana.core.service.user.exception.UserAlreadyVerifiedException;
import com.vntana.core.service.user.exception.UserNotFoundForUuidException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

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

    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(
            final UserRepository userRepository,
            final OrganizationService organizationService,
            final BCryptPasswordEncoder passwordEncoder) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.userRepository = userRepository;
        this.organizationService = organizationService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public User create(final CreateUserDto dto) {
        assertCreateDto(dto);
        LOGGER.debug("Creating user for dto - {}", dto);
        final Organization organization = organizationService.getByUuid(dto.getOrganizationUuid());
        final String password = passwordEncoder.encode(dto.getPassword());
        final User user = new User(dto.getFullName(), dto.getEmail(), password);
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

    @Override
    public User getByUuid(final String uuid) {
        Assert.notNull(uuid, "The user uuid should not be null");
        return (findByUuid(uuid)).orElseThrow(() -> {
            LOGGER.error("Can not find user for uuid - {}", uuid);
            return new UserNotFoundForUuidException(uuid, User.class);
        });
    }

    @Override
    public User makeVerified(final String uuid) {
        LOGGER.debug("Making user verified having uuid - {}", uuid);
        Assert.hasText(uuid, "The user uuid should not be null or empty");
        final User user = getByUuid(uuid);
        if (user.getVerified()) {
            throw new UserAlreadyVerifiedException(String.format("The user having %s uuid is already verified", uuid));
        }
        user.setVerified(true);
        final User updatedUser = userRepository.save(user);
        LOGGER.debug("Successfully made user verified having uuid - {}", uuid);
        return updatedUser;
    }

    private void assertCreateDto(final CreateUserDto dto) {
        Assert.notNull(dto, "The user creation dto should not be null");
        Assert.hasText(dto.getFullName(), "The user full name should not be null or empty");
        Assert.hasText(dto.getEmail(), "The user email should not be null or empty");
        Assert.hasText(dto.getPassword(), "The user password should not be null or empty");
        Assert.hasText(dto.getOrganizationUuid(), "The organization uuid should not be null or empty");
        Assert.notNull(dto.getRole(), "The user role should not be null");
    }
}
