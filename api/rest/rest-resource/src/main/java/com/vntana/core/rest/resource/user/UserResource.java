package com.vntana.core.rest.resource.user;

import com.vntana.core.model.user.request.CreateUserRequest;
import com.vntana.core.model.user.request.FindUserByEmailRequest;
import com.vntana.core.model.user.request.VerifyUserRequest;
import com.vntana.core.model.user.response.CreateUserResponse;
import com.vntana.core.model.user.response.FindUserByEmailResponse;
import com.vntana.core.model.user.response.VerifyUserResponse;
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

    @PutMapping(path = "/verify")
    public ResponseEntity<VerifyUserResponse> verify(@RequestBody final VerifyUserRequest request) {
        LOGGER.debug("Processing user resource verify method for request - {}", request);
        final VerifyUserResponse resultResponse = userServiceFacade.verify(request.getUuid());
        LOGGER.debug("Successfully processed user resource verify method for request - {}", request);
        return ResponseEntity.ok(resultResponse);
    }
}
