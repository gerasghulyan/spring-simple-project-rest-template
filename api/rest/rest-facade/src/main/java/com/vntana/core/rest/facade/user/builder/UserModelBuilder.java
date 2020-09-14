package com.vntana.core.rest.facade.user.builder;

import com.vntana.core.domain.user.User;
import com.vntana.core.model.user.response.get.model.UserViewResponseModel;

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 3:52 PM
 */
public interface UserModelBuilder {

    UserViewResponseModel build(final String uuid);

    UserViewResponseModel build(final User user);
}
