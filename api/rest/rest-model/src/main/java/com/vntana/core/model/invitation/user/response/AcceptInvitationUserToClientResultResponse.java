package com.vntana.core.model.invitation.user.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel;
import com.vntana.core.model.invitation.user.response.model.AcceptInvitationUserToClientResponseModel;

import java.util.List;

/**
 * Created by Diana Gevorgyan
 * Date: 11/24/20
 * Time: 4:40 PM
 */
public class AcceptInvitationUserToClientResultResponse extends AbstractResultResponseModel<AcceptInvitationUserToClientResponseModel, InvitationUserErrorResponseModel> {

    public AcceptInvitationUserToClientResultResponse() {
        super();
    }

    public AcceptInvitationUserToClientResultResponse(final int httpStatusCode, final InvitationUserErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public AcceptInvitationUserToClientResultResponse(final int httpStatusCode, final List<InvitationUserErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public AcceptInvitationUserToClientResultResponse(final AcceptInvitationUserToClientResponseModel response) {
        super(response);
    }
}
