package com.vntana.core.model.invitation.organization.response.model;

import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 12:25 PM
 */
public class CreateInvitationOrganizationResponseModel extends AbstractUuidAwareResponseModel {

    public CreateInvitationOrganizationResponseModel() {
        super();
    }

    public CreateInvitationOrganizationResponseModel(final String uuid) {
        super(uuid);
    }
}
