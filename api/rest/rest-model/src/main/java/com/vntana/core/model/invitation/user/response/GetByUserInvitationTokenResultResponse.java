package com.vntana.core.model.invitation.user.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel;
import com.vntana.core.model.invitation.user.response.model.GetByUserInvitationTokenResponseModel;

import java.util.List;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/15/2020
 * Time: 4:21 PM
 */
public class GetByUserInvitationTokenResultResponse extends AbstractResultResponseModel<GetByUserInvitationTokenResponseModel, InvitationUserErrorResponseModel> {
    public GetByUserInvitationTokenResultResponse() {
        super();
    }

    public GetByUserInvitationTokenResultResponse(final int httpStatusCode, final InvitationUserErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public GetByUserInvitationTokenResultResponse(final int httpStatusCode, final List<InvitationUserErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public GetByUserInvitationTokenResultResponse(final GetByUserInvitationTokenResponseModel response) {
        super(response);
    }
}