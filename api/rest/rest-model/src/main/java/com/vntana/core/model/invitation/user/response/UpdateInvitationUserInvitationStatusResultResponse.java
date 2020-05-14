package com.vntana.core.model.invitation.user.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel;
import com.vntana.core.model.invitation.user.response.model.UpdateInvitationUserInvitationStatusResponseModel;

import java.util.List;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/13/2020
 * Time: 4:34 PM
 */
public class UpdateInvitationUserInvitationStatusResultResponse extends AbstractResultResponseModel<UpdateInvitationUserInvitationStatusResponseModel, InvitationUserErrorResponseModel> {

    public UpdateInvitationUserInvitationStatusResultResponse() {
    }

    public UpdateInvitationUserInvitationStatusResultResponse(final int httpStatusCode, final InvitationUserErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public UpdateInvitationUserInvitationStatusResultResponse(final int httpStatusCode, final List<InvitationUserErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public UpdateInvitationUserInvitationStatusResultResponse(final String uuid) {
        super(new UpdateInvitationUserInvitationStatusResponseModel(uuid));
    }
}