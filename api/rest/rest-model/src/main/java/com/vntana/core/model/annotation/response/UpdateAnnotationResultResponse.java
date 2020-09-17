package com.vntana.core.model.annotation.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.annotation.AnnotationErrorResponseModel;

import java.util.List;

/**
 * Created by Vardan Aivazian
 * Date: 16.09.2020
 * Time: 11:29
 */
public class UpdateAnnotationResultResponse extends AbstractResultResponseModel<UpdateAnnotationResponseModel, AnnotationErrorResponseModel> {

    public UpdateAnnotationResultResponse() {
        super();
    }

    public UpdateAnnotationResultResponse(final int httpStatusCode, final AnnotationErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public UpdateAnnotationResultResponse(final int httpStatusCode, final List<AnnotationErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public UpdateAnnotationResultResponse(final UpdateAnnotationResponseModel response) {
        super(response);
    }
}
