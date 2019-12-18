package com.vntana.core.model.user.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import com.vntana.core.model.user.response.model.FindUserByUuidResponseModel;

import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 12/18/19
 * Time: 11:19 AM
 */
public class FindUserByUuidResponse extends AbstractResultResponseModel<FindUserByUuidResponseModel, UserErrorResponseModel> {

    public FindUserByUuidResponse() {
        super();
    }

    public FindUserByUuidResponse(final UserErrorResponseModel error) {
        super(error);
    }

    public FindUserByUuidResponse(final List<UserErrorResponseModel> errors) {
        super(errors);
    }

    public FindUserByUuidResponse(final FindUserByUuidResponseModel response) {
        super(response);
    }
}
