package com.vntana.core.model.annotation.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.annotation.AnnotationErrorResponseModel;

import java.util.List;

/**
 * Created by Vardan Aivazian
 * Date: 16.09.2020
 * Time: 11:57
 */
public class DeleteAnnotationResultResponse extends AbstractResultResponseModel<DeleteAnnotationResponseModel, AnnotationErrorResponseModel> {

    public DeleteAnnotationResultResponse() {
        super();
    }

    public DeleteAnnotationResultResponse(final int httpStatusCode, final AnnotationErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public DeleteAnnotationResultResponse(final int httpStatusCode, final List<AnnotationErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public DeleteAnnotationResultResponse(final String uuid) {
        super(new DeleteAnnotationResponseModel(uuid));
    }
}
