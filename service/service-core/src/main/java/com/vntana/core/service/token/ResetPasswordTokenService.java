package com.vntana.core.service.token;

import com.vntana.core.domain.token.ResetPasswordToken;
import com.vntana.core.service.token.dto.CreateResetPasswordTokenDto;

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 12:47 PM
 */
public interface ResetPasswordTokenService {
    ResetPasswordToken create(final CreateResetPasswordTokenDto dto);
}
