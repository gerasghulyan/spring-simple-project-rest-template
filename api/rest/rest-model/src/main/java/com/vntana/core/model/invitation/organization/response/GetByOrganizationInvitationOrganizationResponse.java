package com.vntana.core.model.invitation.organization.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel;
import com.vntana.core.model.invitation.organization.response.model.GetByOrganizationInvitationOrganizationResponseModel;

import java.util.List;

/**
 * Created by Geras Ghulyan
 * Date: 3/26/20
 * Time: 2:22 PM
 */
public class GetByOrganizationInvitationOrganizationResponse extends AbstractResultResponseModel<GetByOrganizationInvitationOrganizationResponseModel, InvitationOrganizationErrorResponseModel> {

    public GetByOrganizationInvitationOrganizationResponse() {
        super();
    }

    public GetByOrganizationInvitationOrganizationResponse(final int httpStatusCode, final InvitationOrganizationErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public GetByOrganizationInvitationOrganizationResponse(final int httpStatusCode, final List<InvitationOrganizationErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public GetByOrganizationInvitationOrganizationResponse(final GetByOrganizationInvitationOrganizationResponseModel response) {
        super(response);
    }
}
