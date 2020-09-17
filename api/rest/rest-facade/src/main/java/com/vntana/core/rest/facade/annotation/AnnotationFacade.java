package com.vntana.core.rest.facade.annotation;

import com.vntana.core.model.annotation.request.CreateAnnotationRequestModel;
import com.vntana.core.model.annotation.request.DeleteAnnotationRequestModel;
import com.vntana.core.model.annotation.request.FindAnnotationByFilterRequestModel;
import com.vntana.core.model.annotation.request.UpdateAnnotationRequestModel;
import com.vntana.core.model.annotation.response.AnnotationViewResultResponse;
import com.vntana.core.model.annotation.response.CreateAnnotationResultResponse;
import com.vntana.core.model.annotation.response.DeleteAnnotationResultResponse;
import com.vntana.core.model.annotation.response.UpdateAnnotationResultResponse;

/**
 * Created by Vardan Aivazian
 * Date: 15.09.2020
 * Time: 17:57
 */
public interface AnnotationFacade {

    CreateAnnotationResultResponse create(final CreateAnnotationRequestModel request);

    UpdateAnnotationResultResponse update(final UpdateAnnotationRequestModel request);

    DeleteAnnotationResultResponse delete(final DeleteAnnotationRequestModel request);

    AnnotationViewResultResponse findByFilter(final FindAnnotationByFilterRequestModel request);
}
