package com.vntana.core.rest.facade.common.component;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.model.user.error.UserErrorResponseModel;

/**
 * Created by Diana Gevorgyan.
 * Date: 8/20/2021
 * Time: 11:31 AM
 */
public interface UserEmailSenderComponentPreconditionChecker {

    SingleErrorWithStatus<UserErrorResponseModel> checkUser(final String userEmail);
}
