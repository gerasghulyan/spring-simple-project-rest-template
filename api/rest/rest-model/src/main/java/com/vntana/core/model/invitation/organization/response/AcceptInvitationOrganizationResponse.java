package com.vntana.core.model.invitation.organization.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;
import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel;

/**
 * Created by Geras Ghulyan.
 * Date: 4/9/2020
 * Time: 1:37 PM
 */
public class AcceptInvitationOrganizationResponse extends AbstractResultResponseModel<AbstractUuidAwareResponseModel, InvitationOrganizationErrorResponseModel> {
    public AcceptInvitationOrganizationResponse() {
    }

    public AcceptInvitationOrganizationResponse(final int httpStatusCode, final InvitationOrganizationErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public AcceptInvitationOrganizationResponse(final String uuid) {
        super(new AbstractUuidAwareResponseModel(uuid));
    }
}