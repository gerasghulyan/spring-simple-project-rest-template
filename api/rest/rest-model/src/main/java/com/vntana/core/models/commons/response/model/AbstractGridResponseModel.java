package com.vntana.core.models.commons.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.core.api.models.response.grid.GridResponseModel;
import com.vntana.core.api.models.response.single.ResponseModel;

import java.util.List;

/**
 * Created by Arthur Asatryan.
 * Date: 9/23/19
 * Time: 1:45 PM
 */
public class AbstractGridResponseModel<T extends ResponseModel> implements GridResponseModel<T> {

    @JsonProperty("totalCount")
    private final int totalCount;

    @JsonProperty("grid")
    private final List<T> items;

    public AbstractGridResponseModel(final int totalCount, final List<T> items) {
        this.totalCount = totalCount;
        this.items = items;
    }

    @Override
    public int totalCount() {
        return totalCount;
    }

    @Override
    public List<T> items() {
        return items;
    }
}
