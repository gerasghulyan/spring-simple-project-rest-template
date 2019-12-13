package com.vntana.core.rest.facade.user.component;

/**
 * Created by Arman Gevorgyan.
 * Date: 12/12/19
 * Time: 2:11 PM
 */
public interface UserResetPasswordEmailSenderComponent {

    void sendResetPasswordEmail(final String email, final String token);
}
