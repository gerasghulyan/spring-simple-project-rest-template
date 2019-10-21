package com.vntana.core.service.token.impl;

import com.vntana.core.domain.token.ResetPasswordToken;
import com.vntana.core.domain.user.User;
import com.vntana.core.persistence.token.ResetPasswordTokenRepository;
import com.vntana.core.service.token.ResetPasswordTokenService;
import com.vntana.core.service.token.dto.CreateResetPasswordTokenDto;
import com.vntana.core.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.UUID;

import static java.lang.String.format;

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 12:53 PM
 */
@Service
public class ResetPasswordTokenServiceImpl implements ResetPasswordTokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResetPasswordTokenServiceImpl.class);

    private final UserService userService;

    private final ResetPasswordTokenRepository resetPasswordTokenRepository;

    public ResetPasswordTokenServiceImpl(final UserService userService, final ResetPasswordTokenRepository resetPasswordTokenRepository) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.userService = userService;
        this.resetPasswordTokenRepository = resetPasswordTokenRepository;
    }

    @Transactional
    @Override
    public ResetPasswordToken create(final CreateResetPasswordTokenDto dto) {
        assertCreateResetPasswordTokenDto(dto);
        final User user = userService.findByUuid(dto.getUserUuid()).orElseThrow(() -> {
            LOGGER.error("Can not find user with uuid - {}", dto.getUserUuid());
            return new IllegalStateException(format("Can not find user with uuid - %s", dto.getUserUuid()));
        });
        LOGGER.debug("Persisting reset password token entity for dto - {}", dto);
        return resetPasswordTokenRepository.save(new ResetPasswordToken(UUID.randomUUID().toString(), user));
    }

    private void assertCreateResetPasswordTokenDto(final CreateResetPasswordTokenDto dto) {
        Assert.notNull(dto, "The CreateResetPasswordTokenDto should not be null");
        Assert.notNull(dto.getUserUuid(), "The user uuid should not be null");
    }
}
