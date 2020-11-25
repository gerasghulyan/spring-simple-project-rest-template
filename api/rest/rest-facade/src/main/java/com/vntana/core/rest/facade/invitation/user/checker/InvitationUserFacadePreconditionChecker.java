package com.vntana.core.rest.facade.invitation.user.checker;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel;
import com.vntana.core.model.invitation.user.request.*;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/12/2020
 * Time: 1:55 PM
 */
public interface InvitationUserFacadePreconditionChecker {

    SingleErrorWithStatus<InvitationUserErrorResponseModel> checkCreateInvitationForOrganizationForPossibleErrors(final CreateInvitationForOrganizationUserRequest request);
    
    SingleErrorWithStatus<InvitationUserErrorResponseModel> checkCreateInvitationForClientsForPossibleErrors(final CreateInvitationForOrganizationClientUserRequest request);

    SingleErrorWithStatus<InvitationUserErrorResponseModel> checkUpdateStatusForPossibleErrors(final UpdateInvitationUserInvitationStatusRequest request);

    SingleErrorWithStatus<InvitationUserErrorResponseModel> checkAcceptForOrganizationForPossibleErrors(final AcceptInvitationUserRequest request);

    SingleErrorWithStatus<InvitationUserErrorResponseModel> checkAcceptForClientForPossibleErrors(final AcceptInvitationUserRequest request);

    SingleErrorWithStatus<InvitationUserErrorResponseModel> checkAcceptAndSignInvitationToOrganizationUpForPossibleErrors(final AcceptInvitationUserAndSignUpRequest request);

    SingleErrorWithStatus<InvitationUserErrorResponseModel> checkAcceptAndSignInvitationToClientUpForPossibleErrors(AcceptInvitationUserAndSignUpRequest request);

    SingleErrorWithStatus<InvitationUserErrorResponseModel> checkSendInvitationForOrganizationForPossibleErrors(final SendInvitationForOrganizationUserRequest request);

    SingleErrorWithStatus<InvitationUserErrorResponseModel> checkSendInvitationForClientsForPossibleErrors(final SendInvitationForClientUserRequest request);

    SingleErrorWithStatus<InvitationUserErrorResponseModel> checkGetByTokenForPossibleErrors(final String token);

    SingleErrorWithStatus<InvitationUserErrorResponseModel> checkGetAllByOrganizationUuidAndStatusForPossibleErrors(final GetAllByStatusInvitationUserRequest request);
}