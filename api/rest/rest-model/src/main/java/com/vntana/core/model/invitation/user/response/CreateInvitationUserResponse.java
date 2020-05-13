package com.vntana.core.model.invitation.user.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel;
import com.vntana.core.model.invitation.user.response.model.CreateInvitationUserResponseModel;

import java.util.List;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/12/2020
 * Time: 1:48 PM
 */
public class CreateInvitationUserResponse extends AbstractResultResponseModel<CreateInvitationUserResponseModel, InvitationUserErrorResponseModel> {

    public CreateInvitationUserResponse() {
        super();
    }

    public CreateInvitationUserResponse(final int httpStatusCode, final InvitationUserErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public CreateInvitationUserResponse(final int httpStatusCode, final List<InvitationUserErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public CreateInvitationUserResponse(final String uuid) {
        super(new CreateInvitationUserResponseModel(uuid));
    }
}