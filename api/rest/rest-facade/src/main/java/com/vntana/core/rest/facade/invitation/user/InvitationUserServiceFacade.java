package com.vntana.core.rest.facade.invitation.user;

import com.vntana.core.model.invitation.user.request.*;
import com.vntana.core.model.invitation.user.response.*;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/12/2020
 * Time: 1:52 PM
 */
public interface InvitationUserServiceFacade {

    CreateInvitationUserForOrganizationResultResponse createInvitationForOrganization(final CreateInvitationForOrganizationUserRequest request);

    CreateInvitationUserForOrganizationClientsResultResponse createInvitationForClient(final CreateInvitationForOrganizationClientUserRequest request);

    GetAllByStatusUserInvitationsResultResponse getAllInvitationsToOrganizationByOrganizationUuidAndStatus(final GetAllByStatusInvitationUserRequest request);

    GetAllByStatusUserInvitationsResultResponse getAllInvitationsToClientByOrganizationUuidAndStatus(GetAllByStatusInvitationUserRequest request);

    UpdateInvitationUserInvitationStatusResultResponse updateStatus(final UpdateInvitationUserInvitationStatusRequest request);

    SendInvitationUserResultResponse sendInvitationForOrganization(final SendInvitationForOrganizationUserRequest request);

    SendInvitationUserResultResponse sendInvitationForClients(final SendInvitationForClientUserRequest request);

    AcceptInvitationUserToOrganizationResultResponse acceptInvitationToOrganization(final AcceptInvitationUserRequest request);

    AcceptInvitationUserToClientResultResponse acceptInvitationToClient(AcceptInvitationUserRequest request);

    GetByUserInvitationTokenResultResponse getByTokenInvitationToOrganization(final String token);

    GetByUserInvitationTokenResultResponse getByTokenInvitationToClient(final String token);

    AcceptInvitationUserToOrganizationResultResponse acceptInvitationToOrganizationAndSignUp(final AcceptInvitationUserAndSignUpRequest request);

    AcceptInvitationUserToClientResultResponse acceptInvitationToClientAndSignUp(AcceptInvitationUserAndSignUpRequest request);
}