package com.vntana.core.service.user.impl;

import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.user.User;
import com.vntana.core.domain.user.UserRole;
import com.vntana.core.persistence.user.UserRepository;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.user.UserService;
import com.vntana.core.service.user.dto.CreateUserDto;
import com.vntana.core.service.user.dto.UpdateUserDto;
import com.vntana.core.service.user.dto.UserGrantOrganizationRoleDto;
import com.vntana.core.service.user.exception.UserAlreadyVerifiedException;
import com.vntana.core.service.user.exception.UserNotFoundForTokenException;
import com.vntana.core.service.user.exception.UserNotFoundForUuidException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
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

    @Transactional
    @Override
    public User update(final UpdateUserDto dto) {
        Assert.notNull(dto, "The 'CreateUserDto' dto should not be null");
        LOGGER.debug("Update user for dto - {}", dto);
        final User updatedUser = updateUser(getByUuid(dto.getUuid()), dto);
        userRepository.save(updatedUser);
        LOGGER.debug("Successfully updated user for dto - {}", dto);
        return updatedUser;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findByEmail(final String email) {
        Assert.notNull(email, "The user email should not be null");
        LOGGER.debug("Getting user for email - {}", email);
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findByUuid(final String uuid) {
        Assert.notNull(uuid, "The user uuid should not be null");
        return userRepository.findByUuid(uuid);
    }

    @Transactional(readOnly = true)
    @Override
    public User getByUuid(final String uuid) {
        Assert.notNull(uuid, "The user uuid should not be null");
        return (findByUuid(uuid)).orElseThrow(() -> {
            LOGGER.error("Can not find user for uuid - {}", uuid);
            return new UserNotFoundForUuidException(uuid, User.class);
        });
    }

    @Transactional
    @Override
    public User makeVerified(final String email) {
        LOGGER.debug("Making user verified having uuid - {}", email);
        assertEmail(email);
        final User user = findByEmail(email).orElseThrow(() -> new IllegalStateException(format("Can not find user to verify with email - %s", email)));
        if (Boolean.TRUE.equals(user.getVerified())) {
            throw new UserAlreadyVerifiedException(format("The user having %s uuid is already verified", email));
        }
        user.setVerified(true);
        final User updatedUser = userRepository.save(user);
        LOGGER.debug("Successfully made user verified having uuid - {}", email);
        return updatedUser;
    }

    @Transactional
    @Override
    public User changePassword(final String uuid, final String password) {
        LOGGER.debug("Changing user password having uuid - {}", uuid);
        assertEmail(uuid);
        Assert.hasText(password, "The user password should not be null or empty");
        final User user = getByUuid(uuid);
        user.setPassword(passwordEncoder.encode(password));
        final User updatedUser = userRepository.save(user);
        LOGGER.debug("Successfully changed user password having uuid - {}", uuid);
        return updatedUser;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByUuid(final String uuid) {
        assertEmail(uuid);
        LOGGER.debug("Checking existence of user having uuid - {}", uuid);
        return userRepository.existsByUuid(uuid);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByEmail(final String email) {
        LOGGER.debug("Checking existence of user having email - {}", email);
        Assert.hasText(email, "The email should not be null or empty");
        final boolean exists = userRepository.existsByEmail(email);
        LOGGER.debug("Successfully checked existence of user having email - {}", email);
        return exists;
    }

    @Transactional
    @Override
    public void grantOrganizationRole(final UserGrantOrganizationRoleDto dto) {
        LOGGER.debug("Setting user role for dto - {}", dto);
        Assert.notNull(dto, "The UserGrantOrganizationRoleDto should not be null");
        final User user = getByUuid(dto.getUuid());
        final Organization organization = organizationService.getByUuid(dto.getOrganizationUuid());
        user.grantOrganizationRole(organization);
        LOGGER.debug("Successfully created user role for dto - {}", dto);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean checkPassword(final String uuid, final String rawPassword) {
        Assert.hasText(uuid, "The uuid should not be null or empty");
        Assert.hasText(rawPassword, "The password should not be null or empty");
        LOGGER.debug("Checking user password for user having uuid - {}", uuid);
        final User user = getByUuid(uuid);
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findByRoleAndOrganizationUuid(final UserRole userRole, final String organizationUuid) {
        Assert.notNull(userRole, "The userRole should not be null");
        Assert.hasText(organizationUuid, "The organizationUuid should not be null or empty");
        LOGGER.debug("Retrieving the users having given role - {} on organization having uuid - {}", userRole, organizationUuid);
        final List<User> users = userRepository.findByRoleAndOrganizationUuid(userRole, organizationUuid);
        LOGGER.debug("Successfully retrieved the users having given role - {} on organization having uuid - {}", userRole, organizationUuid);
        return users;
    }

    @Transactional(readOnly = true)
    @Override
    public User getByEmail(final String email) {
        Assert.notNull(email, "The user email should not be null");
        LOGGER.debug("Getting user for email - {}", email);
        return findByEmail(email).orElseThrow(() -> new UserNotFoundForTokenException(String.format("User not found for email %s", email)) );
    }

    private User updateUser(final User user, final UpdateUserDto dto) {
        user.setFullName(dto.getFullName());
        user.setImageBlobId(dto.getImageBlobId());
        return user;
    }

    private void assertCreateDto(final CreateUserDto dto) {
        Assert.notNull(dto, "The user creation dto should not be null");
        Assert.hasText(dto.getFullName(), "The user full name should not be null or empty");
        assertEmail(dto.getEmail());
        Assert.hasText(dto.getPassword(), "The user password should not be null or empty");
        Assert.hasText(dto.getOrganizationUuid(), "The organization uuid should not be null or empty");
        Assert.notNull(dto.getRole(), "The user role should not be null");
    }
    
    private void assertEmail(final String email) {
        Assert.hasText(email, "The email should not be null or empty");
    }
}
