package com.vntana.commons.api.model.request;

import com.vntana.commons.api.model.response.ErrorResponseModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 3:06 PM
 */
public interface ValidatableRequest<E extends ErrorResponseModel> {
    default List<E> validate() {
        return Collections.emptyList();
    }

    default List<E> initializeNew() {
        return new ArrayList<>();
    }
}
