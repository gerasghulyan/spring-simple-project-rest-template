package com.vntana.core.model.organization.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.organization.error.OrganizationErrorResponseModel;

import java.util.List;

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 2:55 PM
 */
public class CheckAvailableOrganizationSlugResultResponse extends AbstractResultResponseModel<CheckAvailableOrganizationSlugResponseModel, OrganizationErrorResponseModel> {
    public CheckAvailableOrganizationSlugResultResponse() {
    }

    public CheckAvailableOrganizationSlugResultResponse(final boolean available, final String suggested) {
        super(new CheckAvailableOrganizationSlugResponseModel(available, suggested));
    }

    public CheckAvailableOrganizationSlugResultResponse(final List<OrganizationErrorResponseModel> errors) {
        super(errors);
    }
}
