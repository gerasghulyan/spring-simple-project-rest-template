package com.vntana.core.model.organization.response.invitation;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.organization.error.OrganizationErrorResponseModel;

/**
 * Created by Geras Ghulyan
 * Date: 3/26/20
 * Time: 2:22 PM
 */
public class GetOrganizationInvitationByOrganizationResponse extends AbstractResultResponseModel<GetOrganizationInvitationByOrganizationResponseModel, OrganizationErrorResponseModel> {

    public GetOrganizationInvitationByOrganizationResponse() {
        super();
    }

    public GetOrganizationInvitationByOrganizationResponse(final int httpStatusCode, final OrganizationErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public GetOrganizationInvitationByOrganizationResponse(final GetOrganizationInvitationByOrganizationResponseModel response) {
        super(response);
    }
}
