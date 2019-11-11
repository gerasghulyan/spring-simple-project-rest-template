package com.vntana.core.model.user.request;

import com.vntana.commons.api.model.request.impl.AbstractUuidAwareRequestModel;

/**
 * Created by Arman Gevorgyan.
 * Date: 11/11/19
 * Time: 1:58 PM
 */
public class VerifyUserRequest extends AbstractUuidAwareRequestModel {

    public VerifyUserRequest() {
        super();
    }

    public VerifyUserRequest(final String uuid) {
        super(uuid);
    }
}
