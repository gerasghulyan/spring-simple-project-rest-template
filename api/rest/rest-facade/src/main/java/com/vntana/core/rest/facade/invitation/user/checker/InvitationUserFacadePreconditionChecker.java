package com.vntana.core.rest.facade.invitation.user.checker;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel;
import com.vntana.core.model.invitation.user.request.AcceptInvitationUserRequest;
import com.vntana.core.model.invitation.user.request.CreateInvitationUserRequest;
import com.vntana.core.model.invitation.user.request.UpdateInvitationUserInvitationStatusRequest;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/12/2020
 * Time: 1:55 PM
 */
public interface InvitationUserFacadePreconditionChecker {

    SingleErrorWithStatus<InvitationUserErrorResponseModel> checkCreateForPossibleErrors(final CreateInvitationUserRequest request);
    
    SingleErrorWithStatus<InvitationUserErrorResponseModel> checkUpdateStatusForPossibleErrors(final UpdateInvitationUserInvitationStatusRequest request);

    SingleErrorWithStatus<InvitationUserErrorResponseModel> checkAcceptForPossibleErrors(final AcceptInvitationUserRequest request);
}