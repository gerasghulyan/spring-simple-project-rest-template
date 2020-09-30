package com.vntana.core.rest.facade.user;

import com.vntana.core.model.auth.response.UserRoleModel;
import com.vntana.core.model.user.request.*;
import com.vntana.core.model.user.response.*;
import com.vntana.core.model.user.response.account.AccountUserResponse;
import com.vntana.core.model.user.response.get.GetUsersByOrganizationResponse;
import com.vntana.core.model.user.response.get.GetUsersByRoleAndOrganizationUuidResponse;
import com.vntana.core.model.user.response.get.GetUsersByUuidsAndOrganizationUuidResponse;

/**
 * Created by Arthur Asatryan.
 * Date: 10/1/19
 * Time: 6:35 PM
 */
public interface UserServiceFacade {
    CreateUserResponse create(final CreateUserRequest request);

    ExistsUserByEmailResponse existsByEmail(final String email);

    FindUserByEmailResponse findByEmail(final FindUserByEmailRequest request);

    FindUserByUuidResponse findByUuid(final String uuid);
    
    GetUsersByUuidsAndOrganizationUuidResponse getByUuidsAndOrganizationUuid(final GetByUuidsAndOrganizationUuidRequest request);

    AccountUserResponse accountDetails(final String uuid);

    VerifyUserResponse verify(final String uuid);

    SendUserVerificationResponse sendVerificationEmail(final SendUserVerificationRequest request);

    SendUserResetPasswordResponse sendResetPasswordEmail(final SendUserResetPasswordRequest request);

    ResetUserPasswordResponse resetPassword(final ResetUserPasswordRequest request);

    UpdateUserResponse update(final UpdateUserRequest request);

    ChangeUserPasswordResponse changePassword(final ChangeUserPasswordRequest request);

    GetUsersByRoleAndOrganizationUuidResponse getByRoleAndOrganizationUuid(final UserRoleModel userRole, final String organizationUuid);

    GetUsersByOrganizationResponse getByOrganizationUuid(final String organizationUuid);

    ResetUserPasswordResponse checkResetPasswordToken(final String token);
}
