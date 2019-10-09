package com.vntana.core.rest.resource.user;

import com.vntana.core.api.models.response.ResultResponseModel;
import com.vntana.core.model.user.request.UserCreateRequest;
import com.vntana.core.model.user.response.UserCreateResponseModel;
import com.vntana.core.rest.facade.user.UserFacade;
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

    private final UserFacade userFacade;

    public UserResource(final UserFacade userFacade) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.userFacade = userFacade;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<ResultResponseModel<UserCreateResponseModel>> createUser(@RequestBody final UserCreateRequest request) {
        LOGGER.debug("Processing resource create for request - {}", request);
        final ResultResponseModel<UserCreateResponseModel> resultResponse = userFacade.createUser(request);
        LOGGER.debug("Successfully processed resource create for request - {}", request);
        return ResponseEntity.ok(resultResponse);
    }
}
