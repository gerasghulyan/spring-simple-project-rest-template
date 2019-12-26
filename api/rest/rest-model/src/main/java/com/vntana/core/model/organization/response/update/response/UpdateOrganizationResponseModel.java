package com.vntana.core.model.organization.response.update.response;

import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;

/**
 * Created by Arman Gevorgyan.
 * Date: 12/26/19
 * Time: 4:02 PM
 */
public class UpdateOrganizationResponseModel extends AbstractUuidAwareResponseModel {

    public UpdateOrganizationResponseModel() {
        super();
    }

    public UpdateOrganizationResponseModel(final String uuid) {
        super(uuid);
    }
}
