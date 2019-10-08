package com.vntana.core.models.user.request;

import com.vntana.core.models.commons.response.model.AbstractUuidAwareResponseModel;

/**
 * Created by Arthur Asatryan.
 * Date: 10/1/19
 * Time: 6:43 PM
 */
public class UserCreateResponseModel extends AbstractUuidAwareResponseModel {

    private UserCreateResponseModel() {
        super();
    }

    public UserCreateResponseModel(final String uuid) {
        super(uuid);
    }
}
