package com.vntana.core.api.models.response.grid;

import com.vntana.core.api.models.response.single.ResponseModel;

import java.util.List;

/**
 * Created by Arthur Asatryan.
 * Date: 9/23/19
 * Time: 1:41 PM
 */
public interface GridResponseModel<T extends ResponseModel> extends ResponseModel {

    int totalCount();

    List<T> items();
}
