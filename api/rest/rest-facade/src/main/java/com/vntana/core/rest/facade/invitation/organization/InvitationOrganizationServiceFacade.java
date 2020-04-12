package com.vntana.core.rest.facade.invitation.organization;

import com.vntana.core.model.invitation.organization.request.*;
import com.vntana.core.model.invitation.organization.response.*;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 12:05 PM
 */
public interface InvitationOrganizationServiceFacade {

    CreateInvitationOrganizationResponse create(final CreateInvitationOrganizationRequest request);

    GetInvitationOrganizationResponse getByUuid(final String uuid);

    SendInvitationOrganizationResponse sendInvitation(final SendInvitationOrganizationRequest request);

    UpdateInvitationOrganizationStatusResponse updateStatus(final UpdateInvitationOrganizationStatusRequest request);

    RejectInvitationOrganizationResponse reject(final RejectInvitationOrganizationRequest request);

    AcceptInvitationOrganizationResponse accept(final AcceptInvitationOrganizationRequest request);
}
