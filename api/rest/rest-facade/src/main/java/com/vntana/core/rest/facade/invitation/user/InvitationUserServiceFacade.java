package com.vntana.core.rest.facade.invitation.user;

import com.vntana.core.model.invitation.user.request.*;
import com.vntana.core.model.invitation.user.response.*;

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

    AcceptInvitationUserResultResponse acceptAndSignUp(final AcceptInvitationUserAndSignUpRequest request);
}