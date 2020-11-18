package com.vntana.core.rest.facade.invitation.user;

import com.vntana.core.model.invitation.user.request.*;
import com.vntana.core.model.invitation.user.response.*;
import com.vntana.core.model.invitation.user.request.AcceptInvitationUserRequest;
import com.vntana.core.model.invitation.user.request.CreateInvitationForOrganizationUserRequest;
import com.vntana.core.model.invitation.user.request.GetAllByStatusInvitationUserRequest;
import com.vntana.core.model.invitation.user.request.SendInvitationForOrganizationUserRequest;
import com.vntana.core.model.invitation.user.request.UpdateInvitationUserInvitationStatusRequest;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/12/2020
 * Time: 1:52 PM
 */
public interface InvitationUserServiceFacade {

    CreateInvitationUserForOrganizationResultResponse createInvitationForOrganization(final CreateInvitationForOrganizationUserRequest request);

    CreateInvitationUserForOrganizationClientsResultResponse createInvitationForClient(final CreateInvitationForOrganizationClientUserRequest request);

    GetAllByStatusUserInvitationsResultResponse getAllByOrganizationUuidAndStatus(final GetAllByStatusInvitationUserRequest request);

    UpdateInvitationUserInvitationStatusResultResponse updateStatus(final UpdateInvitationUserInvitationStatusRequest request);

    SendInvitationUserResultResponse sendInvitationForOrganization(final SendInvitationForOrganizationUserRequest request);

    SendInvitationUserResultResponse sendInvitationForClients(final SendInvitationForClientUserRequest request);

    AcceptInvitationUserResultResponse accept(final AcceptInvitationUserRequest request);

    GetByUserInvitationTokenResultResponse getByToken(final String token);

    AcceptInvitationUserResultResponse acceptAndSignUp(final AcceptInvitationUserAndSignUpRequest request);
}