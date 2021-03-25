package com.vntana.core.service.token.reset.password;

import com.vntana.core.domain.token.TokenResetPassword;
import com.vntana.core.domain.user.User;
import com.vntana.core.persistence.token.resetpassword.TokenResetPasswordRepository;
import com.vntana.core.service.token.reset.password.dto.CreateTokenResetPasswordDto;
import com.vntana.core.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Created by Geras Ghulyan
 * Date: 6/23/20
 * Time: 11:22 AM
 */
@Service
public class TokenResetPasswordServiceImpl implements TokenResetPasswordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenResetPasswordServiceImpl.class);

    private final UserService userService;
    private final TokenResetPasswordRepository tokenResetPasswordRepository;

    public TokenResetPasswordServiceImpl(final UserService userService, final TokenResetPasswordRepository tokenResetPasswordRepository) {
        this.userService = userService;
        this.tokenResetPasswordRepository = tokenResetPasswordRepository;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Transactional
    @Override
    public TokenResetPassword create(final CreateTokenResetPasswordDto dto) {
        Assert.notNull(dto, "The AuthTokenCreateDto should not be null");
        LOGGER.debug("Creating ResetPasswordToken for user - {}", dto.getUserUuid());
        final User user = userService.getByUuid(dto.getUserUuid());
        final TokenResetPassword token = new TokenResetPassword(dto.getToken(), dto.getExpirationDate(), user);
        final TokenResetPassword saveToken = tokenResetPasswordRepository.save(token);
        LOGGER.debug("Successfully creating auth token for user - {}", dto.getUserUuid());
        return saveToken;
    }
}
