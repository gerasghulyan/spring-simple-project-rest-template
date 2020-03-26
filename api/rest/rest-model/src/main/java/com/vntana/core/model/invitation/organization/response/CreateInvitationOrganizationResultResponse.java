package com.vntana.core.model.invitation.organization.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel;
import com.vntana.core.model.invitation.organization.response.model.CreateInvitationOrganizationResponseModel;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 12:07 PM
 */
public class CreateInvitationOrganizationResultResponse extends AbstractResultResponseModel<CreateInvitationOrganizationResponseModel, InvitationOrganizationErrorResponseModel> {

    public CreateInvitationOrganizationResultResponse() {
        super();
    }

    public CreateInvitationOrganizationResultResponse(final int httpStatusCode, final InvitationOrganizationErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public CreateInvitationOrganizationResultResponse(final String uuid) {
        super(new CreateInvitationOrganizationResponseModel(uuid));
    }
}
