package com.vntana.core.model.invitation.organization.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel;
import com.vntana.core.model.invitation.organization.response.model.SendInvitationOrganizationResponseModel;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/27/20
 * Time: 5:11 PM
 */
public class SendInvitationOrganizationResponse extends AbstractResultResponseModel<SendInvitationOrganizationResponseModel, InvitationOrganizationErrorResponseModel> {

    public SendInvitationOrganizationResponse() {
        super();
    }

    public SendInvitationOrganizationResponse(final int httpStatusCode, final InvitationOrganizationErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public SendInvitationOrganizationResponse(final SendInvitationOrganizationResponseModel response) {
        super(response);
    }
}
