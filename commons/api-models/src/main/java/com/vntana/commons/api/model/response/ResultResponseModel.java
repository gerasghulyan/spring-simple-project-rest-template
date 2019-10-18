package com.vntana.commons.api.model.response;

import java.util.List;

/**
 * Created by Arthur Asatryan.
 * Date: 9/23/19
 * Time: 11:39 AM
 */
public interface ResultResponseModel<R extends ResponseModel, E extends ErrorResponseModel> {
    List<E> errors();

    void errors(final List<E> errors);

    boolean success();

    void success(final boolean success);

    R response();
}
