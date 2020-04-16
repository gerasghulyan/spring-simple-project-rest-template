package com.vntana.core.model.user.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import com.vntana.core.model.user.response.model.ExistsUserByEmailResponseModel;

import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 4/6/20
 * Time: 10:16 AM
 */
public class ExistsUserByEmailResponse extends AbstractResultResponseModel<ExistsUserByEmailResponseModel, UserErrorResponseModel> {

    public ExistsUserByEmailResponse() {
        super();
    }

    public ExistsUserByEmailResponse(final int httpStatusCode, final UserErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public ExistsUserByEmailResponse(final int httpStatusCode, final List<UserErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public ExistsUserByEmailResponse(final Boolean exists) {
        super(new ExistsUserByEmailResponseModel(exists));
    }
}
