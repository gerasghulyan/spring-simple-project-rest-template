package com.vntana.core.model.user.response.model;

import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;

/**
 * Created by Manuk Gharslyan.
 * Date: 1/24/2020
 * Time: 10:20 AM
 */
public class ChangeUserPasswordResponseModel extends AbstractUuidAwareResponseModel {

    public ChangeUserPasswordResponseModel() {
    }

    public ChangeUserPasswordResponseModel(final String uuid) {
        super(uuid);
    }
}