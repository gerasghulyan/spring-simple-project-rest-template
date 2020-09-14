package com.vntana.core.rest.facade.user.builder.impl;

import com.vntana.core.domain.user.User;
import com.vntana.core.model.user.response.get.model.UserViewResponseModel;
import com.vntana.core.rest.facade.user.builder.UserModelBuilder;
import com.vntana.core.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 3:52 PM
 */
@Component
class UserModelBuilderImpl implements UserModelBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserModelBuilderImpl.class);

    private final UserService userService;

    public UserModelBuilderImpl(final UserService userService) {
        this.userService = userService;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Override
    public UserViewResponseModel build(final String uuid) {
        LOGGER.debug("Building user view model for the provided user uuid - {}", uuid);
        Assert.hasText(uuid, "The user uuid should not be null or empty");
        final User user = userService.getByUuid(uuid);
        final UserViewResponseModel model = build(user);
        LOGGER.debug("Successfully built user view model for the provided user uuid - {}, result - {}", uuid, model);
        return model;
    }

    @Override
    public UserViewResponseModel build(final User user) {
        LOGGER.debug("Building user view model for the provided user - {}", user);
        Assert.notNull(user, "The user should not be null");
        final UserViewResponseModel model = new UserViewResponseModel(
                user.getUuid(),
                user.getFullName()
        );
        LOGGER.debug("Successfully built user view model for the provided user - {}, result - {}", user, model);
        return model;
    }
}
