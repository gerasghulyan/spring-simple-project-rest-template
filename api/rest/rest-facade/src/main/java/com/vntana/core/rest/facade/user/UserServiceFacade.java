package com.vntana.core.rest.facade.user;

import com.vntana.core.model.auth.response.UserRoleModel;
import com.vntana.core.model.user.request.*;
import com.vntana.core.model.user.response.*;
import com.vntana.core.model.user.response.account.GetUserByOrganizationResponse;
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

    GetUserByOrganizationResponse getUserByOrganization(final String userUuid, final String organizationUuid);

    VerifyUserResponse verify(final String uuid);

    SendUserVerificationResponse sendVerificationEmail(final SendUserVerificationRequest request);

    SendUserResetPasswordResponse sendResetPasswordEmail(final SendUserResetPasswordRequest request);

    ResetUserPasswordResponse resetPassword(final ResetUserPasswordRequest request);

    UpdateUserResponse update(final UpdateUserRequest request);

    ChangeUserPasswordResponse changePassword(final ChangeUserPasswordRequest request);

    GetUsersByRoleAndOrganizationUuidResponse getByRoleAndOrganizationUuid(final UserRoleModel userRole, final String organizationUuid);

    /**
     * Gets user having access to give organization (client organization is not included. For this purpose see getClientsByOrganization method)
     *
     * @param organizationUuid
     * @return
     */
    GetUsersByOrganizationResponse getByOrganizationUuid(final String organizationUuid);

    /**
     * Gets all users having access to given client
     *
     * @param clientUuid
     * @return
     */
    GetUsersByOrganizationResponse getByClientOrganizationUuid(final String clientUuid);

    /**
     * Gets all users having access to organization's clients
     *
     * @param organizationUuid
     * @return
     */
    GetUsersByOrganizationResponse getClientsByOrganization(final String organizationUuid);

    ResetUserPasswordResponse checkResetPasswordToken(final String token);
}
