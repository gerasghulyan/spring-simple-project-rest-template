package com.vntana.core.rest.facade.token.impl;

import com.vntana.core.model.token.error.TokenErrorResponseModel;
import com.vntana.core.model.token.request.ResetPasswordRequest;
import com.vntana.core.model.token.response.ResetPasswordResultResponse;
import com.vntana.core.rest.facade.token.ResetPasswordTokenServiceFacade;
import com.vntana.core.service.token.ResetPasswordTokenService;
import com.vntana.core.service.token.dto.CreateResetPasswordTokenDto;
import com.vntana.core.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 3:25 PM
 */
@Component
public class ResetPasswordTokenServiceFacadeImpl implements ResetPasswordTokenServiceFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResetPasswordTokenServiceFacadeImpl.class);

    private final UserService userService;

    private final ResetPasswordTokenService resetPasswordTokenService;

    public ResetPasswordTokenServiceFacadeImpl(final UserService userService, final ResetPasswordTokenService resetPasswordTokenService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.userService = userService;
        this.resetPasswordTokenService = resetPasswordTokenService;
    }

    @Override
    public ResetPasswordResultResponse reset(final ResetPasswordRequest request) {
        return userService.findByEmail(request.getEmail())
                .map(user -> {
                    resetPasswordTokenService.create(new CreateResetPasswordTokenDto(user.getUuid()));
                    return new ResetPasswordResultResponse();
                })
                .orElseGet(() -> new ResetPasswordResultResponse(Collections.singletonList(TokenErrorResponseModel.USER_NOT_FOUND)));
    }
}
