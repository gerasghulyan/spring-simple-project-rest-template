package com.vntana.core.rest.resource.user;

import com.vntana.core.model.user.request.*;
import com.vntana.core.model.user.response.*;
import com.vntana.core.rest.facade.user.UserServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Arthur Asatryan.
 * Date: 10/2/19
 * Time: 10:41 AM
 */
@RestController
@RequestMapping(value = "/users",
        produces = "application/json"
)
public class UserResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

    private final UserServiceFacade userServiceFacade;

    public UserResource(final UserServiceFacade userServiceFacade) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.userServiceFacade = userServiceFacade;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody final CreateUserRequest request) {
        LOGGER.debug("Processing resource create for request - {}", request);
        final CreateUserResponse resultResponse = userServiceFacade.create(request);
        LOGGER.debug("Successfully processed resource create for request - {}", request);
        return ResponseEntity.ok(resultResponse);
    }

    @PostMapping(path = "/by-email")
    public ResponseEntity<FindUserByEmailResponse> findByEmail(@RequestBody final FindUserByEmailRequest request) {
        LOGGER.debug("Processing find user by email request - {}", request);
        final FindUserByEmailResponse resultResponse = userServiceFacade.findByEmail(request);
        LOGGER.debug("Successfully proceeded find user by email request with response - {}", resultResponse);
        return ResponseEntity.ok(resultResponse);
    }

    @GetMapping(path = "/{uuid}/account-details/organizations/{organizationUuid}")
    public ResponseEntity<AccountUserResponse> accountDetails(
            @PathVariable("uuid") final String uuid,
            @PathVariable("organizationUuid") final String organizationUuid) {
        LOGGER.debug("Processing find user account by uuid - {} and organizationUuid - {}", uuid, organizationUuid);
        final AccountUserResponse response = userServiceFacade.accountDetails(uuid, organizationUuid);
        LOGGER.debug("Successfully proceeded find user account with response - {}", response);
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/verify")
    public ResponseEntity<VerifyUserResponse> verify(@RequestBody final VerifyUserRequest request) {
        LOGGER.debug("Processing user resource verify method for request - {}", request);
        final VerifyUserResponse resultResponse = userServiceFacade.verify(request.getEmail());
        LOGGER.debug("Successfully processed user resource verify method for request - {}", request);
        return ResponseEntity.ok(resultResponse);
    }

    @PostMapping(path = "/send-verification")
    public ResponseEntity<SendUserVerificationResponse> sendVerification(@RequestBody final SendUserVerificationRequest request) {
        LOGGER.debug("Processing user resource send verification method for request - {}", request);
        final SendUserVerificationResponse resultResponse = userServiceFacade.sendVerificationEmail(request);
        LOGGER.debug("Successfully processed user resource send verification method for request - {}", request);
        return ResponseEntity.ok(resultResponse);
    }

    @PostMapping(path = "/send-reset-password")
    public ResponseEntity<SendUserResetPasswordResponse> sendResetPasswordEmail(@RequestBody final SendUserResetPasswordRequest request) {
        LOGGER.debug("Processing user resource sendResetPasswordEmail method for request - {}", request);
        final SendUserResetPasswordResponse resultResponse = userServiceFacade.sendResetPasswordEmail(request);
        LOGGER.debug("Successfully processed user resource sendResetPasswordEmail method for request - {}", request);
        return ResponseEntity.ok(resultResponse);
    }

    @PutMapping(path = "/reset-password")
    public ResponseEntity<ResetUserPasswordResponse> resetPassword(@RequestBody final ResetUserPasswordRequest request) {
        LOGGER.debug("Processing user resource resetPassword method for email - {}", request.getEmail());
        final ResetUserPasswordResponse resultResponse = userServiceFacade.resetPassword(request);
        LOGGER.debug("Successfully processed user resource resetPassword method for email - {}", request.getEmail());
        return ResponseEntity.ok(resultResponse);
    }
}
