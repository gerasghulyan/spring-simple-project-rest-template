package com.vntana.core.model.invitation.user.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel;
import com.vntana.core.model.invitation.user.response.model.GetAllByStatusUserInvitationsGridResponseModel;

import java.util.List;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/13/2020
 * Time: 3:19 PM
 */
public class GetAllByStatusUserInvitationsResultResponse extends AbstractResultResponseModel<GetAllByStatusUserInvitationsGridResponseModel, InvitationUserErrorResponseModel> {

    public GetAllByStatusUserInvitationsResultResponse() {
        super();
    }

    public GetAllByStatusUserInvitationsResultResponse(final int httpStatusCode, final InvitationUserErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public GetAllByStatusUserInvitationsResultResponse(final int httpStatusCode, final List<InvitationUserErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public GetAllByStatusUserInvitationsResultResponse(final GetAllByStatusUserInvitationsGridResponseModel response) {
        super(response);
    }
}