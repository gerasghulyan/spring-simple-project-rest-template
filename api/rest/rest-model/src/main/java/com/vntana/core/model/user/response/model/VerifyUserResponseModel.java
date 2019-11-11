package com.vntana.core.model.user.response.model;

import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;

/**
 * Created by Arman Gevorgyan.
 * Date: 11/11/19
 * Time: 1:29 PM
 */
public class VerifyUserResponseModel extends AbstractUuidAwareResponseModel {

    private VerifyUserResponseModel() {
        super();
    }

    public VerifyUserResponseModel(final String uuid) {
        super(uuid);
    }
}
