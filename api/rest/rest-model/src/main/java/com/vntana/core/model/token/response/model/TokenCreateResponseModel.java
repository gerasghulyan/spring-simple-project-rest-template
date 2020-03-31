package com.vntana.core.model.token.response.model;

import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/27/20
 * Time: 11:34 AM
 */
public class TokenCreateResponseModel extends AbstractUuidAwareResponseModel {

    public TokenCreateResponseModel() {
        super();
    }

    public TokenCreateResponseModel(final String uuid) {
        super(uuid);
    }
}
