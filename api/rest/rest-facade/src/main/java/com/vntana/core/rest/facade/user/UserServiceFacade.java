package com.vntana.core.rest.facade.user;

import com.vntana.core.model.user.request.*;
import com.vntana.core.model.user.response.*;

/**
 * Created by Arthur Asatryan.
 * Date: 10/1/19
 * Time: 6:35 PM
 */
public interface UserServiceFacade {
    CreateUserResponse create(final CreateUserRequest request);

    FindUserByEmailResponse findByEmail(final FindUserByEmailRequest request);

    FindUserByUuidResponse findByUuid(final String uuid);

    AccountUserResponse accountDetails(final String uuid, final String organizationUuid);

    VerifyUserResponse verify(final String uuid);

    SendUserVerificationResponse sendVerificationEmail(final SendUserVerificationRequest request);

    SendUserResetPasswordResponse sendResetPasswordEmail(final SendUserResetPasswordRequest request);

    ResetUserPasswordResponse resetPassword(final ResetUserPasswordRequest request);

    UpdateUserResponse update(final UpdateUserRequest request);
}
