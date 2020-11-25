package com.vntana.core.model.invitation.user.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel;
import com.vntana.core.model.invitation.user.response.model.AcceptInvitationUserToOrganizationResponseModel;

import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/14/20
 * Time: 5:31 PM
 */
public class AcceptInvitationUserToOrganizationResultResponse extends AbstractResultResponseModel<AcceptInvitationUserToOrganizationResponseModel, InvitationUserErrorResponseModel> {

    public AcceptInvitationUserToOrganizationResultResponse() {
        super();
    }

    public AcceptInvitationUserToOrganizationResultResponse(final int httpStatusCode, final InvitationUserErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public AcceptInvitationUserToOrganizationResultResponse(final int httpStatusCode, final List<InvitationUserErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public AcceptInvitationUserToOrganizationResultResponse(final AcceptInvitationUserToOrganizationResponseModel response) {
        super(response);
    }
}
