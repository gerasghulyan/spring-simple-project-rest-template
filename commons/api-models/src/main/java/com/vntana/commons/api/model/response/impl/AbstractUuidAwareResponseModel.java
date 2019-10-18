package com.vntana.commons.api.model.response.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.ResponseModel;

/**
 * Created by Arthur Asatryan.
 * Date: 10/1/19
 * Time: 6:50 PM
 */
public class AbstractUuidAwareResponseModel implements ResponseModel {

    @JsonProperty("uuid")
    private String uuid;

    protected AbstractUuidAwareResponseModel() {
    }

    public AbstractUuidAwareResponseModel(final String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }
}
