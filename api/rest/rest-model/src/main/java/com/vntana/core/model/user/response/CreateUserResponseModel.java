package com.vntana.core.model.user.response;

import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;

/**
 * Created by Arthur Asatryan.
 * Date: 10/1/19
 * Time: 6:43 PM
 */
public class CreateUserResponseModel extends AbstractUuidAwareResponseModel {

    private CreateUserResponseModel() {
        super();
    }

    public CreateUserResponseModel(final String uuid) {
        super(uuid);
    }
}
