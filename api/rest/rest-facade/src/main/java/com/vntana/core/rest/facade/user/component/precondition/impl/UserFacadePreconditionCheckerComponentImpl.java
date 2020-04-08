package com.vntana.core.rest.facade.user.component.precondition.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import com.vntana.core.rest.facade.user.component.precondition.UserFacadePreconditionCheckerComponent;
import com.vntana.core.service.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.vntana.core.model.user.error.UserErrorResponseModel.MISSING_UUID;
import static com.vntana.core.model.user.error.UserErrorResponseModel.NOT_FOUND_FOR_UUID;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_UNPROCESSABLE_ENTITY;

/**
 * Created by Arman Gevorgyan.
 * Date: 4/7/20
 * Time: 6:52 PM
 */
@Component
public class UserFacadePreconditionCheckerComponentImpl implements UserFacadePreconditionCheckerComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserFacadePreconditionCheckerComponentImpl.class);

    private final UserService userService;

    public UserFacadePreconditionCheckerComponentImpl(final UserService userService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.userService = userService;
    }

    @Override
    public SingleErrorWithStatus<UserErrorResponseModel> checkAccountDetails(final String userUuid) {
        LOGGER.debug("processing precondition check for user facade account details where userUuid - {}", userUuid);
        if (StringUtils.isBlank(userUuid)) {
            return SingleErrorWithStatus.of(SC_UNPROCESSABLE_ENTITY, MISSING_UUID);
        }
        if (!userService.existsByUuid(userUuid)) {
            return SingleErrorWithStatus.of(SC_NOT_FOUND, NOT_FOUND_FOR_UUID);
        }
        return SingleErrorWithStatus.empty();
    }
}
