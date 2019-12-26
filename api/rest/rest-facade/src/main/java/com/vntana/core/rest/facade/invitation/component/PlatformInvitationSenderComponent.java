package com.vntana.core.rest.facade.invitation.component;

/**
 * Created by Arman Gevorgyan.
 * Date: 12/25/19
 * Time: 5:46 PM
 */
public interface PlatformInvitationSenderComponent {

    void sendInvitation(final String email, final String token);
}
