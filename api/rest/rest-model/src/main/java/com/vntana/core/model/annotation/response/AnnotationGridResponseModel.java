package com.vntana.core.model.annotation.response;

import com.vntana.commons.api.model.response.impl.AbstractGridResponseModel;

import java.util.List;

/**
 * Created by Vardan Aivazian
 * Date: 16.09.2020
 * Time: 14:32
 */
public class AnnotationGridResponseModel extends AbstractGridResponseModel<AnnotationViewResponseModel> {

    public AnnotationGridResponseModel() {
    }

    public AnnotationGridResponseModel(final int totalCount, final List<AnnotationViewResponseModel> items) {
        super(totalCount, items);
    }
}
