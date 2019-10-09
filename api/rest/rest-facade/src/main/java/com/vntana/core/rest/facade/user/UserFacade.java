package com.vntana.core.rest.facade.user;

import com.vntana.core.model.user.request.UserCreateRequest;
import com.vntana.core.model.user.response.UserCreateResultResponse;

/**
 * Created by Arthur Asatryan.
 * Date: 10/1/19
 * Time: 6:35 PM
 */
public interface UserFacade {
    UserCreateResultResponse createUser(final UserCreateRequest request);
}
