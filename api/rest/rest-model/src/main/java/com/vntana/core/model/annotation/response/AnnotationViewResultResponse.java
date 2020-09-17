package com.vntana.core.model.annotation.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.annotation.AnnotationErrorResponseModel;

import java.util.List;

/**
 * Created by Vardan Aivazian
 * Date: 16.09.2020
 * Time: 11:57
 */
public class AnnotationViewResultResponse extends AbstractResultResponseModel<AnnotationGridResponseModel, AnnotationErrorResponseModel> {

    public AnnotationViewResultResponse() {
    }

    public AnnotationViewResultResponse(final int httpStatusCode, final AnnotationErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public AnnotationViewResultResponse(final int httpStatusCode, final List<AnnotationErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public AnnotationViewResultResponse(final AnnotationGridResponseModel response) {
        super(response);
    }
}
