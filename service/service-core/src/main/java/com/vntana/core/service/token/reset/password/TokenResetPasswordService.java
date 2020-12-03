package com.vntana.core.service.token.reset.password;

import com.vntana.core.domain.token.TokenResetPassword;
import com.vntana.core.service.token.reset.password.dto.CreateTokenResetPasswordDto;

/**
 * Created by Geras Ghulyan
 * Date: 6/23/20
 * Time: 11:20 AM
 */
public interface TokenResetPasswordService {

    TokenResetPassword create(final CreateTokenResetPasswordDto dto);
}
