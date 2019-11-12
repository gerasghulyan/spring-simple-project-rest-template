package com.vntana.core.model.user.response.model;

import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;

/**
 * Created by Arman Gevorgyan.
 * Date: 11/12/19
 * Time: 1:32 PM
 */
public class SendUserVerificationResponseModel extends AbstractUuidAwareResponseModel {

    public SendUserVerificationResponseModel() {
        super();
    }

    public SendUserVerificationResponseModel(final String uuid) {
        super(uuid);
    }
}
