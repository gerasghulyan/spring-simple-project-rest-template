package com.vntana.core.model.client.response;

import com.vntana.core.model.commons.response.model.AbstractUuidAwareResponseModel;

/**
 * Created by Arthur Asatryan.
 * Date: 10/10/19
 * Time: 10:42 AM
 */
public class CreateClientOrganizationResponseModel extends AbstractUuidAwareResponseModel {
    public CreateClientOrganizationResponseModel() {
    }

    CreateClientOrganizationResponseModel(final String uuid) {
        super(uuid);
    }
}
