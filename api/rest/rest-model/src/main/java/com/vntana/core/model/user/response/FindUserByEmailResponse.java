package com.vntana.core.model.user.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import com.vntana.core.model.user.response.model.FindUserByEmailResponseModel;

import java.util.List;

/**
 * Created by Arthur Asatryan.
 * Date: 10/18/19
 * Time: 5:07 PM
 */
public class FindUserByEmailResponse extends AbstractResultResponseModel<FindUserByEmailResponseModel, UserErrorResponseModel> {
    public FindUserByEmailResponse() {
    }

    public FindUserByEmailResponse(final FindUserByEmailResponseModel response) {
        super(response);
    }

    public FindUserByEmailResponse(final List<UserErrorResponseModel> errors) {
        super(errors);
    }
}