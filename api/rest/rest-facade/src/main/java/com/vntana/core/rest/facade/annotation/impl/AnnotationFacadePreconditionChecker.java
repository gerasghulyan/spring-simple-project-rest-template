package com.vntana.core.rest.facade.annotation.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.model.annotation.AnnotationErrorResponseModel;
import com.vntana.core.model.annotation.request.CreateAnnotationRequestModel;
import com.vntana.core.model.annotation.request.DeleteAnnotationRequestModel;
import com.vntana.core.model.annotation.request.UpdateAnnotationRequestModel;

/**
 * Created by Vardan Aivazian
 * Date: 16.09.2020
 * Time: 11:26
 */
public interface AnnotationFacadePreconditionChecker {

    SingleErrorWithStatus<AnnotationErrorResponseModel> checkCreateAnnotation(final CreateAnnotationRequestModel request);

    SingleErrorWithStatus<AnnotationErrorResponseModel> checkUpdateAnnotation(final UpdateAnnotationRequestModel request);

    SingleErrorWithStatus<AnnotationErrorResponseModel> checkDeleteAnnotation(final DeleteAnnotationRequestModel request);
}
