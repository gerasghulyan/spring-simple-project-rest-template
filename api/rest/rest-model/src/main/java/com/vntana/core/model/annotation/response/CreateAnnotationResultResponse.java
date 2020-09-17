package com.vntana.core.model.annotation.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.annotation.AnnotationErrorResponseModel;

import java.util.List;

/**
 * Created by Vardan Aivazian
 * Date: 15.09.2020
 * Time: 17:58
 */
public class CreateAnnotationResultResponse extends AbstractResultResponseModel<CreateAnnotationResponseModel, AnnotationErrorResponseModel> {

    public CreateAnnotationResultResponse() {
        super();
    }

    public CreateAnnotationResultResponse(final int httpStatusCode, final AnnotationErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public CreateAnnotationResultResponse(final int httpStatusCode, final List<AnnotationErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public CreateAnnotationResultResponse(final String uuid) {
        super(new CreateAnnotationResponseModel(uuid));
    }
}
