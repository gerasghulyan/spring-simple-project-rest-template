package com.vntana.core.model.invitation.user.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel;
import com.vntana.core.model.invitation.user.response.model.CreateInvitationUserForClientsResponseModel;

import java.util.List;

/**
 * Created by Diana Gevorgyan
 * Date: 11/10/20
 * Time: 1:51 PM
 */
public class CreateInvitationUserForOrganizationClientsResultResponse extends AbstractResultResponseModel<CreateInvitationUserForClientsResponseModel, InvitationUserErrorResponseModel> {

    public CreateInvitationUserForOrganizationClientsResultResponse(final int httpStatusCode, final InvitationUserErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public CreateInvitationUserForOrganizationClientsResultResponse(final int httpStatusCode, final List<InvitationUserErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public CreateInvitationUserForOrganizationClientsResultResponse(final List<String> uuids) {
        new CreateInvitationUserForClientsResponseModel(uuids);
    }
}
