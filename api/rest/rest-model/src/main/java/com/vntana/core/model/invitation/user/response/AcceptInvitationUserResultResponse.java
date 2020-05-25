package com.vntana.core.model.invitation.user.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel;
import com.vntana.core.model.invitation.user.response.model.AcceptInvitationUserResponseModel;

import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/14/20
 * Time: 5:31 PM
 */
public class AcceptInvitationUserResultResponse extends AbstractResultResponseModel<AcceptInvitationUserResponseModel, InvitationUserErrorResponseModel> {

    public AcceptInvitationUserResultResponse() {
        super();
    }

    public AcceptInvitationUserResultResponse(final int httpStatusCode, final InvitationUserErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public AcceptInvitationUserResultResponse(final int httpStatusCode, final List<InvitationUserErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public AcceptInvitationUserResultResponse(final AcceptInvitationUserResponseModel response) {
        super(response);
    }
}
