package com.vntana.core.rest.facade.user;

import com.vntana.core.model.user.request.CreateUserRequest;
import com.vntana.core.model.user.response.CreateUserResultResponse;

/**
 * Created by Arthur Asatryan.
 * Date: 10/1/19
 * Time: 6:35 PM
 */
public interface UserServiceFacade {
    CreateUserResultResponse create(final CreateUserRequest request);
}
