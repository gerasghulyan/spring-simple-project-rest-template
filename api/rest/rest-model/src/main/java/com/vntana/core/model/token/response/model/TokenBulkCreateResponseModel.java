package com.vntana.core.model.token.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.ResponseModel;

import java.util.List;

/**
 * Created by Diana Gevorgyan
 * Date: 11/19/20
 * Time: 4:03 PM
 */
public class TokenBulkCreateResponseModel implements ResponseModel {
    
    @JsonProperty("uuids")
    private List<String> uuids;

    public TokenBulkCreateResponseModel() {
    }

    public TokenBulkCreateResponseModel(final List<String> uuids) {
        this.uuids = uuids;
    }
}
