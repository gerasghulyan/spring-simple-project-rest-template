package com.vntana.core.model.user.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.organization.error.OrganizationErrorResponseModel;
import com.vntana.core.model.user.response.model.GetUserOrganizationsGridResponseModel;

import java.util.List;

/**
 * Created by Geras Ghulyan
 * Date: 04.11.19
 * Time: 16:30
 */
public class UserOrganizationResponse extends AbstractResultResponseModel<GetUserOrganizationsGridResponseModel, OrganizationErrorResponseModel> {

    public UserOrganizationResponse() {
    }

    public UserOrganizationResponse(final List<OrganizationErrorResponseModel> errors) {
        super(errors);
    }

    public UserOrganizationResponse(final GetUserOrganizationsGridResponseModel response) {
        super(response);
    }

}