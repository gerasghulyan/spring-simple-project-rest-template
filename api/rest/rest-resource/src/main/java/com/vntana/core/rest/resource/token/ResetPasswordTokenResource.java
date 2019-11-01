package com.vntana.core.rest.resource.token;

import com.vntana.core.model.token.request.ResetPasswordRequest;
import com.vntana.core.model.token.response.ResetPasswordResultResponse;
import com.vntana.core.rest.facade.token.ResetPasswordTokenServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 3:44 PM
 */
@RestController
@RequestMapping(value = "/tokens",
        consumes = "application/json",
        produces = "application/json"
)
public class ResetPasswordTokenResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResetPasswordTokenResource.class);

    private final ResetPasswordTokenServiceFacade resetPasswordTokenServiceFacade;

    public ResetPasswordTokenResource(final ResetPasswordTokenServiceFacade resetPasswordTokenServiceFacade) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.resetPasswordTokenServiceFacade = resetPasswordTokenServiceFacade;
    }

    @PostMapping(path = "/reset-password")
    public ResponseEntity<ResetPasswordResultResponse> reset(@RequestBody final ResetPasswordRequest request) {
        LOGGER.debug("Processing reset user password with request - {}", request);
        final ResetPasswordResultResponse resultResponse = resetPasswordTokenServiceFacade.reset(request);
        LOGGER.debug("Successfully processed reset password request with response - {}", resultResponse);
        return ResponseEntity.ok(resultResponse);
    }
}
