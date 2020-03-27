package com.vntana.core.rest.facade.invitation.organization.component;

import com.vntana.core.model.invitation.organization.request.SendInvitationOrganizationRequest;
import com.vntana.core.model.invitation.organization.response.SendInvitationOrganizationResponse;

/**
 * Created by Arman Gevorgyan.
 * Date: 11/12/19
 * Time: 3:09 PM
 */
public interface InvitationOrganizationSenderComponent {

    SendInvitationOrganizationResponse sendInvitation(final SendInvitationOrganizationRequest request);
}
