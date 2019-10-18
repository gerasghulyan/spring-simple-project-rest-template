package com.vntana.commons.api.model.response.impl;

import com.vntana.commons.api.model.response.ErrorResponseModel;
import com.vntana.commons.api.model.response.ResponseModel;

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
