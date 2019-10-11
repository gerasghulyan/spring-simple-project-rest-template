package com.vntana.core.service.user.impl;

import com.vntana.core.domain.client.ClientOrganization;
import com.vntana.core.domain.user.User;
import com.vntana.core.persistence.user.UserRepository;
import com.vntana.core.service.client.ClientOrganizationService;
import com.vntana.core.service.user.UserService;
import com.vntana.core.service.user.dto.UserCreateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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

    private final ClientOrganizationService clientOrganizationService;

    public UserServiceImpl(final UserRepository userRepository, final ClientOrganizationService clientOrganizationService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.userRepository = userRepository;
        this.clientOrganizationService = clientOrganizationService;
    }

    @Transactional
    @Override
    public User create(final UserCreateDto dto) {
        assertCreateDto(dto);
        LOGGER.debug("Creating user for dto - {}", dto);
        final ClientOrganization clientOrganization = getClientOrganization(dto);
        final User user = new User(dto.getFullName(), dto.getEmail(), dto.getPassword());
        final User savedUser = userRepository.save(user);
        savedUser.grant(clientOrganization, dto.getRole());
        LOGGER.debug("Successfully created user for dto - {}", dto);
        return savedUser;
    }

    private void assertCreateDto(final UserCreateDto dto) {
        Assert.notNull(dto, "The user creation dto should not be null");
        Assert.hasText(dto.getFullName(), "The user full name should not be null or empty");
        Assert.hasText(dto.getEmail(), "The user email should not be null or empty");
        Assert.hasText(dto.getPassword(), "The user password should not be null or empty");
        Assert.hasText(dto.getClientOrganizationUuid(), "The user client organization uuid should not be null or empty");
        Assert.notNull(dto.getRole(), "The user role should not be null");
    }

    private ClientOrganization getClientOrganization(final UserCreateDto dto) {
        return clientOrganizationService.findByUuid(dto.getClientOrganizationUuid()).orElseThrow(() -> {
            LOGGER.error("Can not find client organization for uuid - {}", dto.getClientOrganizationUuid());
            return new IllegalStateException(format("Can not find client organization for uuid - %s", dto.getClientOrganizationUuid()));
        });
    }
}
