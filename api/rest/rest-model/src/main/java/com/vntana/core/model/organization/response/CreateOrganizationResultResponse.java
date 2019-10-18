package com.vntana.core.model.organization.response;

import com.vntana.core.model.commons.response.result.AbstractResultResponseModel;
import com.vntana.core.model.organization.error.OrganizationErrorResponseModel;

import java.util.List;

/**
 * Created by Arthur Asatryan.
 * Date: 10/10/19
 * Time: 10:42 AM
 */
public class CreateOrganizationResultResponse extends AbstractResultResponseModel<CreateOrganizationResponseModel, OrganizationErrorResponseModel> {
    public CreateOrganizationResultResponse() {
    }

    public CreateOrganizationResultResponse(final String uuid) {
        super(new CreateOrganizationResponseModel(uuid));
    }

    public CreateOrganizationResultResponse(final List<OrganizationErrorResponseModel> errors) {
        super(errors);
    }
}
