package com.vntana.core.model.invitation.organization.response;

import com.vntana.commons.api.model.response.impl.EmptyResultResponseModel;
import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel;

/**
 * Created by Manuk Gharslyan.
 * Date: 4/9/2020
 * Time: 1:37 PM
 */
public class RejectInvitationOrganizationResponse extends EmptyResultResponseModel<InvitationOrganizationErrorResponseModel> {
    public RejectInvitationOrganizationResponse() {
    }
    
    public RejectInvitationOrganizationResponse(final int httpStatusCode, final InvitationOrganizationErrorResponseModel error) {
        super(httpStatusCode, error);
    }
}