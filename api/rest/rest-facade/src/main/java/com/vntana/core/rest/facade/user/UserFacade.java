package com.vntana.core.rest.facade.user;

import com.vntana.core.api.models.response.ResultResponseModel;
import com.vntana.core.models.user.request.UserCreateResponseModel;
import com.vntana.core.models.user.response.UserCreateRequest;

/**
 * Created by Arthur Asatryan.
 * Date: 10/1/19
 * Time: 6:35 PM
 */
public interface UserFacade {

    ResultResponseModel<UserCreateResponseModel> createUser(final UserCreateRequest request);
}
