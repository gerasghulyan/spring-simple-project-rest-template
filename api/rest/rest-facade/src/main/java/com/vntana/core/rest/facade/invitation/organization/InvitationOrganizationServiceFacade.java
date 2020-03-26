package com.vntana.core.rest.facade.invitation.organization;

import com.vntana.core.model.invitation.organization.request.CreateInvitationOrganizationRequest;
import com.vntana.core.model.invitation.organization.response.CreateInvitationOrganizationResultResponse;
import com.vntana.core.model.invitation.organization.response.GetInvitationOrganizationResultResponse;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 12:05 PM
 */
public interface InvitationOrganizationServiceFacade {

    CreateInvitationOrganizationResultResponse create(final CreateInvitationOrganizationRequest request);

    GetInvitationOrganizationResultResponse getByUuid(final String uuid);
}
