package com.vntana.core.service.user.impl;

import com.vntana.core.domain.user.User;
import com.vntana.core.persistence.user.UserRepository;
import com.vntana.core.service.user.UserService;
import com.vntana.core.service.user.dto.UserCreateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Created by Arthur Asatryan.
 * Date: 10/3/19
 * Time: 6:39 PM
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;

    public UserServiceImpl(final UserRepository userRepository) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(final UserCreateDto dto) {
        LOGGER.debug("Creating user for dto - {}", dto);
        assertCreateDto(dto);
        final User user = new User(dto.getFirstName(), dto.getSecondName());
        final User savedUser = userRepository.save(user);
        LOGGER.debug("Successfully created user for dto - {}", dto);
        return savedUser;
    }

    private void assertCreateDto(final UserCreateDto dto) {
        Assert.notNull(dto, "The user creation dto should not be null");
        Assert.hasText(dto.getFirstName(), "The user first name should not be null or empty");
        Assert.hasText(dto.getSecondName(), "The user second name should not be null or empty");
    }
}
