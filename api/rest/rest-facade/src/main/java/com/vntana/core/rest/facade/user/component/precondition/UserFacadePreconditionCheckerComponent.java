package com.vntana.core.rest.facade.user.component.precondition;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import com.vntana.core.model.user.request.GetByUuidsAndOrganizationUuidRequest;

/**
 * Created by Arman Gevorgyan.
 * Date: 4/7/20
 * Time: 6:51 PM
 */
public interface UserFacadePreconditionCheckerComponent {

    SingleErrorWithStatus<UserErrorResponseModel> checkAccountDetails(final String userUuid);

    SingleErrorWithStatus<UserErrorResponseModel> checkGetUserByOrganization(final String userUuid, final String organizationUuid);

    SingleErrorWithStatus<UserErrorResponseModel> checkGetByOrganizationUuid(final String organizationUuid);

    SingleErrorWithStatus<UserErrorResponseModel> checkGetByClientOrganizationUuid(final String clientUuid);

    SingleErrorWithStatus<UserErrorResponseModel> checkGetByUuidsAndOrganizationUuid(final GetByUuidsAndOrganizationUuidRequest request);
}
