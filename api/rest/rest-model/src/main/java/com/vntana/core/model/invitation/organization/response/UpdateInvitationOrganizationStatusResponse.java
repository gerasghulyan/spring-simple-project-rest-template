package com.vntana.core.model.invitation.organization.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel;
import com.vntana.core.model.invitation.organization.response.model.UpdateInvitationOrganizationStatusResponseModel;

/**
 * Created by Manuk Gharslyan.
 * Date: 4/9/2020
 * Time: 11:06 AM
 */
public class UpdateInvitationOrganizationStatusResponse extends AbstractResultResponseModel<UpdateInvitationOrganizationStatusResponseModel, InvitationOrganizationErrorResponseModel> {
    public UpdateInvitationOrganizationStatusResponse() {
    }

    public UpdateInvitationOrganizationStatusResponse(final int httpStatusCode, final InvitationOrganizationErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public UpdateInvitationOrganizationStatusResponse(final String uuid) {
        super(new UpdateInvitationOrganizationStatusResponseModel(uuid));
    }
}