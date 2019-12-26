package com.vntana.core.model.invitation.response;

import com.vntana.commons.api.model.response.impl.EmptyResultResponseModel;
import com.vntana.core.model.invitation.error.InvitationErrorResponseModel;

import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 12/25/19
 * Time: 5:35 PM
 */
public class InvitationToPlatformResponse extends EmptyResultResponseModel<InvitationErrorResponseModel> {

    public InvitationToPlatformResponse() {
        super();
    }

    public InvitationToPlatformResponse(final InvitationErrorResponseModel error) {
        super(error);
    }

    public InvitationToPlatformResponse(final List<InvitationErrorResponseModel> errors) {
        super(errors);
    }
}
