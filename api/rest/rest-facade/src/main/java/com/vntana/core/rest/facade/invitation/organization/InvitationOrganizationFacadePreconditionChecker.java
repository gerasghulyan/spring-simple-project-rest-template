package com.vntana.core.rest.facade.invitation.organization;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel;
import com.vntana.core.model.invitation.organization.request.CreateInvitationOrganizationRequest;
import com.vntana.core.model.invitation.organization.request.RejectInvitationOrganizationRequest;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 2:27 PM
 */
public interface InvitationOrganizationFacadePreconditionChecker {

    SingleErrorWithStatus<InvitationOrganizationErrorResponseModel> checkCreateForPossibleErrors(final CreateInvitationOrganizationRequest request);

    SingleErrorWithStatus<InvitationOrganizationErrorResponseModel> checkGetByUuidForPossibleErrors(final String uuid);

    SingleErrorWithStatus<InvitationOrganizationErrorResponseModel> checkRejectInvitationForPossibleErrors(final RejectInvitationOrganizationRequest request);
}
