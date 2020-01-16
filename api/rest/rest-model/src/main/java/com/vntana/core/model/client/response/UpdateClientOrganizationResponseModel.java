package com.vntana.core.model.client.response;

import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;

/**
 * Created by Geras Ghulyan.
 * Date: 10/10/19
 * Time: 10:42 AM
 */
public class UpdateClientOrganizationResponseModel extends AbstractUuidAwareResponseModel {
    public UpdateClientOrganizationResponseModel() {
    }

    UpdateClientOrganizationResponseModel(final String uuid) {
        super(uuid);
    }
}
