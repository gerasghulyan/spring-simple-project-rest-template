package com.vntana.core.rest.facade.annotation.builder;

import com.vntana.core.domain.annotation.Annotation;
import com.vntana.core.model.annotation.response.AnnotationViewResponseModel;

/**
 * Created by Vardan Aivazian
 * Date: 16.09.2020
 * Time: 14:20
 */
public interface AnnotationViewModelBuilder {
    
    AnnotationViewResponseModel build(final Annotation annotation);
}
