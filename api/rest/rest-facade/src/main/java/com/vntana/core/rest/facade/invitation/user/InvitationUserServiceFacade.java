package com.vntana.core.rest.facade.invitation.user;

import com.vntana.core.model.invitation.user.request.CreateInvitationUserRequest;
import com.vntana.core.model.invitation.user.response.CreateInvitationUserResponse;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/12/2020
 * Time: 1:52 PM
 */
public interface InvitationUserServiceFacade {

    CreateInvitationUserResponse create(final CreateInvitationUserRequest request);
}