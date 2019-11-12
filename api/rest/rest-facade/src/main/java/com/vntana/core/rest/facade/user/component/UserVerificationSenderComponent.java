package com.vntana.core.rest.facade.user.component;

import com.vntana.core.model.user.request.SendUserVerificationRequest;
import com.vntana.core.model.user.response.SendUserVerificationResponse;

/**
 * Created by Arman Gevorgyan.
 * Date: 11/12/19
 * Time: 3:09 PM
 */
public interface UserVerificationSenderComponent {

    SendUserVerificationResponse sendVerificationEmail(final SendUserVerificationRequest request);
}
