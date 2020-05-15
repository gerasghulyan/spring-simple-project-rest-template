package com.vntana.core.model.invitation.user.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel;
import com.vntana.core.model.invitation.user.response.model.SendInvitationUserResponseModel;

import java.util.List;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/15/2020
 * Time: 11:02 AM
 */
public class SendInvitationUserResultResponse extends AbstractResultResponseModel<SendInvitationUserResponseModel, InvitationUserErrorResponseModel> {

    public SendInvitationUserResultResponse() {
        super();
    }

    public SendInvitationUserResultResponse(final int httpStatusCode, final InvitationUserErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public SendInvitationUserResultResponse(final int httpStatusCode, final List<InvitationUserErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public SendInvitationUserResultResponse(final SendInvitationUserResponseModel response) {
        super(response);
    }
}