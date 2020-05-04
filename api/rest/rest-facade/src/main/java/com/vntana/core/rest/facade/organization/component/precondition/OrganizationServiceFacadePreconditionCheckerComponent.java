package com.vntana.core.rest.facade.organization.component.precondition;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.model.organization.error.OrganizationErrorResponseModel;

/**
 * Created by Geras Ghulyan
 * Date: 26.04.20
 * Time: 23:10
 */
public interface OrganizationServiceFacadePreconditionCheckerComponent {

    SingleErrorWithStatus<OrganizationErrorResponseModel> checkGetOrganizationInvitation(final String organizationUuid);

}
