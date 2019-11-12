package com.vntana.core.rest.facade.user;

import com.vntana.core.model.user.request.CreateUserRequest;
import com.vntana.core.model.user.request.FindUserByEmailRequest;
import com.vntana.core.model.user.request.SendUserVerificationRequest;
import com.vntana.core.model.user.response.*;

/**
 * Created by Arthur Asatryan.
 * Date: 10/1/19
 * Time: 6:35 PM
 */
public interface UserServiceFacade {
    CreateUserResponse create(final CreateUserRequest request);

    FindUserByEmailResponse findByEmail(final FindUserByEmailRequest request);

    AccountUserResponse accountDetails(final String uuid, final String organizationUuid);

    VerifyUserResponse verify(final String uuid);

    SendUserVerificationResponse sendVerificationEmail(final SendUserVerificationRequest request);
}
