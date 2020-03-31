package com.vntana.core.rest.facade.invitation.organization;

import com.vntana.core.model.invitation.organization.request.CreateInvitationOrganizationRequest;
import com.vntana.core.model.invitation.organization.request.SendInvitationOrganizationRequest;
import com.vntana.core.model.invitation.organization.response.CreateInvitationOrganizationResponse;
import com.vntana.core.model.invitation.organization.response.GetInvitationOrganizationResponse;
import com.vntana.core.model.invitation.organization.response.SendInvitationOrganizationResponse;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 12:05 PM
 */
public interface InvitationOrganizationServiceFacade {

    CreateInvitationOrganizationResponse create(final CreateInvitationOrganizationRequest request);

    GetInvitationOrganizationResponse getByUuid(final String uuid);

    SendInvitationOrganizationResponse sendInvitation(final SendInvitationOrganizationRequest request);

}
