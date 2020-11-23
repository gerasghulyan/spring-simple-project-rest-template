package com.vntana.core.rest.facade.invitation.user.component;

import com.vntana.core.model.invitation.user.request.SendInvitationForClientUserRequest;
import com.vntana.core.model.invitation.user.request.SendInvitationForOrganizationUserRequest;
import com.vntana.core.model.invitation.user.response.SendInvitationUserResultResponse;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/15/2020
 * Time: 11:11 AM
 */
public interface InvitationUserSenderComponent {

    SendInvitationUserResultResponse sendInvitationForOrganization(final SendInvitationForOrganizationUserRequest request);

    SendInvitationUserResultResponse sendInvitationForClients(final SendInvitationForClientUserRequest request);
}