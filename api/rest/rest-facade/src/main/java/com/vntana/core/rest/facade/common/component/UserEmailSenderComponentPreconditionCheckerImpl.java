package com.vntana.core.rest.facade.common.component;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.domain.user.User;
import com.vntana.core.domain.user.UserSource;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import com.vntana.core.service.user.UserService;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by Diana Gevorgyan.
 * Date: 8/20/2021
 * Time: 11:35 AM
 */
@Component
public class UserEmailSenderComponentPreconditionCheckerImpl implements UserEmailSenderComponentPreconditionChecker {

    private final UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserEmailSenderComponentPreconditionCheckerImpl.class);

    public UserEmailSenderComponentPreconditionCheckerImpl(final UserService userService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.userService = userService;
    }

    @Override
    public SingleErrorWithStatus<UserErrorResponseModel> checkUser(final String userEmail) {
        LOGGER.debug("Processing email sender precondition checker component for user - {}", userEmail);
        final Optional<User> user = userService.findByEmail(userEmail);
        if (!user.isPresent()) {
            LOGGER.debug("Cannot find user for provided email - {}", userEmail);
            return SingleErrorWithStatus.of(HttpStatus.SC_UNPROCESSABLE_ENTITY, UserErrorResponseModel.NOT_FOUND_FOR_UUID);
        }
        if (user.get().getSource().equals(UserSource.EXTERNAL)) {
            LOGGER.debug("Email cannot be sent to  user with provided email - {}, user is - {}", userEmail, user.get().getSource());
            return SingleErrorWithStatus.of(HttpStatus.SC_UNPROCESSABLE_ENTITY, UserErrorResponseModel.INVALID_USER);
        }
        return SingleErrorWithStatus.empty();
    }
}
