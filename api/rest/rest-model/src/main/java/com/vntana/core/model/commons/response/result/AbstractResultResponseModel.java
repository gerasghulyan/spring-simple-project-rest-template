package com.vntana.core.model.commons.response.result;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.core.api.models.response.ResultResponseModel;
import com.vntana.core.api.models.response.error.ErrorResponseModel;
import com.vntana.core.api.models.response.single.ResponseModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by Arthur Asatryan.
 * Date: 10/1/19
 * Time: 6:44 PM
 */
public class AbstractResultResponseModel<T extends ResponseModel, E extends ErrorResponseModel> implements ResultResponseModel<T, E> {

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("errors")
    private List<E> errors;

    @JsonProperty("response")
    private T response;

    protected AbstractResultResponseModel() {
        super();
    }

    public AbstractResultResponseModel(final boolean success, final List<E> errors, final T response) {
        this.success = success;
        this.errors = errors;
        this.response = response;
    }

    public AbstractResultResponseModel(final List<E> errors) {
        this.success = false;
        this.errors = errors;
        this.response = null;
    }

    public AbstractResultResponseModel(final T response) {
        this.success = true;
        this.errors = Collections.emptyList();
        this.response = response;
    }

    @Override
    public List<E> errors() {
        return errors;
    }

    @Override
    public void errors(final List<E> errors) {
        this.errors = errors;
    }

    @Override
    public boolean success() {
        return success;
    }

    public void success(final boolean success) {
        this.success = success;
    }

    @Override
    public T response() {
        return response;
    }
}
