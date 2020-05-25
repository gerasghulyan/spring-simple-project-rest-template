package com.vntana.core.model.user.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.organization.error.OrganizationErrorResponseModel;
import com.vntana.core.model.user.response.get.model.GetUserOrganizationsGridResponseModel;

/**
 * Created by Geras Ghulyan
 * Date: 04.11.19
 * Time: 16:30
 */
public class UserOrganizationResponse extends AbstractResultResponseModel<GetUserOrganizationsGridResponseModel, OrganizationErrorResponseModel> {

    public UserOrganizationResponse() {
        super();
    }

    public UserOrganizationResponse(final GetUserOrganizationsGridResponseModel response) {
        super(response);
    }

    public UserOrganizationResponse(final int httpStatusCode, final OrganizationErrorResponseModel error) {
        super(httpStatusCode, error);
    }
}