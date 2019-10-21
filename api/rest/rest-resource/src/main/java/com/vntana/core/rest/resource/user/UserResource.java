package com.vntana.core.rest.resource.user;

import com.vntana.core.model.user.request.CreateUserRequest;
import com.vntana.core.model.user.request.FindUserByEmailRequest;
import com.vntana.core.model.user.response.CreateUserResultResponse;
import com.vntana.core.model.user.response.FindUserByEmailResultResponse;
import com.vntana.core.rest.facade.user.UserServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Arthur Asatryan.
 * Date: 10/2/19
 * Time: 10:41 AM
 */
@RestController
@RequestMapping(value = "/users",
        consumes = "application/json",
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
    public ResponseEntity<CreateUserResultResponse> createUser(@RequestBody final CreateUserRequest request) {
        LOGGER.debug("Processing resource create for request - {}", request);
        final CreateUserResultResponse resultResponse = userServiceFacade.create(request);
        LOGGER.debug("Successfully processed resource create for request - {}", request);
        return ResponseEntity.ok(resultResponse);
    }

    @PostMapping(path = "/by-email")
    public ResponseEntity<FindUserByEmailResultResponse> createUser(@RequestBody final FindUserByEmailRequest request) {
        LOGGER.debug("Processing find user by email request - {}", request);
        final FindUserByEmailResultResponse resultResponse = userServiceFacade.findByEmail(request);
        LOGGER.debug("Successfully proceeded find user by email request with response - {}", resultResponse);
        return ResponseEntity.ok(resultResponse);
    }
}
