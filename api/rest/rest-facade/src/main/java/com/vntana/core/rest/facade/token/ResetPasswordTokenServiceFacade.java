package com.vntana.core.rest.facade.token;

import com.vntana.core.model.token.request.ResetPasswordRequest;
import com.vntana.core.model.token.response.ResetPasswordResultResponse;

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 3:24 PM
 */
public interface ResetPasswordTokenServiceFacade {
    ResetPasswordResultResponse reset(final ResetPasswordRequest request);
}
