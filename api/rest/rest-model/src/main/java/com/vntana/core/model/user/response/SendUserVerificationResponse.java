package com.vntana.core.model.user.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import com.vntana.core.model.user.response.model.SendUserVerificationResponseModel;

import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 11/12/19
 * Time: 1:31 PM
 */
public class SendUserVerificationResponse extends AbstractResultResponseModel<SendUserVerificationResponseModel, UserErrorResponseModel> {

    public SendUserVerificationResponse() {
        super();
    }

    public SendUserVerificationResponse(final List<UserErrorResponseModel> errors) {
        super(errors);
    }

    public SendUserVerificationResponse(final String userUuid) {
        super(new SendUserVerificationResponseModel(userUuid));
    }
}
