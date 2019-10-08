package com.vntana.core.api.models.response;


import com.vntana.core.api.models.response.error.ErrorResponseModel;
import com.vntana.core.api.models.response.single.ResponseModel;

import java.util.List;

/**
 * Created by Arthur Asatryan.
 * Date: 9/23/19
 * Time: 11:39 AM
 */
public interface ResultResponseModel<R extends ResponseModel> {

    <E extends ErrorResponseModel> List<E> errors();

    boolean success();

    R response();
}
