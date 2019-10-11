package com.vntana.core.model.commons.response.result;

import com.vntana.core.api.models.response.error.ErrorResponseModel;
import com.vntana.core.api.models.response.single.ResponseModel;

import java.util.List;

/**
 * Created by Arthur Asatryan.
 * Date: 10/11/19
 * Time: 5:39 PM
 */
public class ErrorResultResponseModel extends AbstractResultResponseModel<ErrorResultResponseModel, ErrorResponseModel> implements ResponseModel {
    public ErrorResultResponseModel() {
        super();
    }

    public ErrorResultResponseModel(final List<ErrorResponseModel> errors) {
        super(errors);
    }
}
