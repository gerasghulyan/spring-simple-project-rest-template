package com.vntana.core.model.user.response.model;

import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;

/**
 * Created by Manuk Gharslyan.
 * Date: 1/23/2020
 * Time: 4:09 PM
 */
public class UpdateUserResponseModel extends AbstractUuidAwareResponseModel {

    public UpdateUserResponseModel() {
        super();
    }

    public UpdateUserResponseModel(final String uuid) {
        super(uuid);
    }
}