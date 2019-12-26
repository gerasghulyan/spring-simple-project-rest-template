package com.vntana.core.model.organization.response.create;

import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;

/**
 * Created by Arthur Asatryan.
 * Date: 10/10/19
 * Time: 10:42 AM
 */
public class CreateOrganizationResponseModel extends AbstractUuidAwareResponseModel {
    public CreateOrganizationResponseModel() {
    }

    CreateOrganizationResponseModel(final String uuid) {
        super(uuid);
    }
}
