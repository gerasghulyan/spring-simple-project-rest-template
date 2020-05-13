package com.vntana.core.model.invitation.user.response.model;

import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/12/2020
 * Time: 1:49 PM
 */
public class CreateInvitationUserResponseModel extends AbstractUuidAwareResponseModel {
    public CreateInvitationUserResponseModel() {
        super();
    }

    public CreateInvitationUserResponseModel(final String uuid) {
        super(uuid);
    }
}