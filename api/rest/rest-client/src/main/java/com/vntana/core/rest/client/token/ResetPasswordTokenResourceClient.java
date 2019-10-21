package com.vntana.core.rest.client.token;

import com.vntana.core.model.token.request.ResetPasswordRequest;
import com.vntana.core.model.token.response.ResetPasswordResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 3:48 PM
 */
@FeignClient(name = "coreTokensResetPassword", path = "tokens", url = "${ms.core.url}")
public interface ResetPasswordTokenResourceClient {
    @PostMapping(path = "/reset-password")
    ResetPasswordResultResponse reset(@RequestBody final ResetPasswordRequest request);
}
