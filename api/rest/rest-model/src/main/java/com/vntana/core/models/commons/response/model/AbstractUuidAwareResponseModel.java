package com.vntana.core.models.commons.response.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.core.api.models.response.single.UuidAwareResponseModel;

/**
 * Created by Arthur Asatryan.
 * Date: 10/1/19
 * Time: 6:50 PM
 */
public class AbstractUuidAwareResponseModel implements UuidAwareResponseModel {

    @JsonProperty("uuid")
    private String uuid;


    protected AbstractUuidAwareResponseModel() {
        super();
    }

    public AbstractUuidAwareResponseModel(final String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String uuid() {
        return uuid;
    }
}
