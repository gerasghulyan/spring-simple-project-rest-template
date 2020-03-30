package com.vntana.core.model.invitation.organization.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel;
import com.vntana.core.model.invitation.organization.response.model.GetInvitationOrganizationResponseModel;

import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 2:22 PM
 */
public class GetInvitationOrganizationResponse extends AbstractResultResponseModel<GetInvitationOrganizationResponseModel, InvitationOrganizationErrorResponseModel> {

    public GetInvitationOrganizationResponse() {
        super();
    }

    public GetInvitationOrganizationResponse(final int httpStatusCode, final InvitationOrganizationErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public GetInvitationOrganizationResponse(final int httpStatusCode, final List<InvitationOrganizationErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public GetInvitationOrganizationResponse(final GetInvitationOrganizationResponseModel response) {
        super(response);
    }
}
