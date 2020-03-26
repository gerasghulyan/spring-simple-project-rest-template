package com.vntana.core.model.invitation.organization.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel;
import com.vntana.core.model.invitation.organization.response.model.GetInvitationOrganizationResponseModel;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 2:22 PM
 */
public class GetInvitationOrganizationResultResponse extends AbstractResultResponseModel<GetInvitationOrganizationResponseModel, InvitationOrganizationErrorResponseModel> {

    public GetInvitationOrganizationResultResponse() {
        super();
    }

    public GetInvitationOrganizationResultResponse(final int httpStatusCode, final InvitationOrganizationErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public GetInvitationOrganizationResultResponse(final GetInvitationOrganizationResponseModel response) {
        super(response);
    }
}
