package com.vntana.core.model.indexation;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.indexation.error.OrganizationIndexationByUuidErrorResponseModel;
import com.vntana.core.model.indexation.model.OrganizationIndexationByUuidResponseModel;

/**
 * Created by Geras Ghulyan
 * Date: 6/5/20
 * Time: 6:18 PM
 */
public class OrganizationIndexationByUuidResultResponse  extends AbstractResultResponseModel<OrganizationIndexationByUuidResponseModel, OrganizationIndexationByUuidErrorResponseModel> {
    public OrganizationIndexationByUuidResultResponse() {
    }

    public OrganizationIndexationByUuidResultResponse(final Long tookMillis) {
        super(new OrganizationIndexationByUuidResponseModel(tookMillis));
    }

    public OrganizationIndexationByUuidResultResponse(final int httpStatusCode, final OrganizationIndexationByUuidErrorResponseModel error) {
        super(httpStatusCode, error);
    }
}