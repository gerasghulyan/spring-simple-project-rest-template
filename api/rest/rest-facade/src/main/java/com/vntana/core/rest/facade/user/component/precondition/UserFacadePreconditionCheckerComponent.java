package com.vntana.core.rest.facade.user.component.precondition;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.model.user.error.UserErrorResponseModel;

/**
 * Created by Arman Gevorgyan.
 * Date: 4/7/20
 * Time: 6:51 PM
 */
public interface UserFacadePreconditionCheckerComponent {

    SingleErrorWithStatus<UserErrorResponseModel> checkAccountDetails(final String userUuid);
}
