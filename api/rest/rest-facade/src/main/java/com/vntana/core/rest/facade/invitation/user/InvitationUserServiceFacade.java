package com.vntana.core.rest.facade.invitation.user;

import com.vntana.core.model.invitation.user.request.AcceptInvitationUserRequest;
import com.vntana.core.model.invitation.user.request.CreateInvitationUserRequest;
import com.vntana.core.model.invitation.user.request.GetAllByStatusInvitationUserRequest;
import com.vntana.core.model.invitation.user.request.SendInvitationUserRequest;
import com.vntana.core.model.invitation.user.request.UpdateInvitationUserInvitationStatusRequest;
import com.vntana.core.model.invitation.user.response.AcceptInvitationUserResultResponse;
import com.vntana.core.model.invitation.user.response.CreateInvitationUserResultResponse;
import com.vntana.core.model.invitation.user.response.GetAllByStatusUserInvitationsResultResponse;
import com.vntana.core.model.invitation.user.response.SendInvitationUserResultResponse;
import com.vntana.core.model.invitation.user.response.UpdateInvitationUserInvitationStatusResultResponse;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/12/2020
 * Time: 1:52 PM
 */
public interface InvitationUserServiceFacade {

    CreateInvitationUserResultResponse create(final CreateInvitationUserRequest request);

    GetAllByStatusUserInvitationsResultResponse getAllByStatus(final GetAllByStatusInvitationUserRequest request);

    UpdateInvitationUserInvitationStatusResultResponse updateStatus(final UpdateInvitationUserInvitationStatusRequest request);

    SendInvitationUserResultResponse sendInvitation(final SendInvitationUserRequest request);

    AcceptInvitationUserResultResponse accept(final AcceptInvitationUserRequest request);
}