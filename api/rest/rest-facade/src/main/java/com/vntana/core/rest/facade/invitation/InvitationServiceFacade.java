package com.vntana.core.rest.facade.invitation;

import com.vntana.core.model.invitation.request.InvitationToPlatformRequest;
import com.vntana.core.model.invitation.response.InvitationToPlatformResponse;

/**
 * Created by Arman Gevorgyan.
 * Date: 12/26/19
 * Time: 10:22 AM
 */
public interface InvitationServiceFacade {

    InvitationToPlatformResponse inviteToPlatform(final InvitationToPlatformRequest request);
}
