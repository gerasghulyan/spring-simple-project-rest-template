package com.vntana.core.rest.facade.annotation.builder.impl;

import com.vntana.core.domain.annotation.Annotation;
import com.vntana.core.model.annotation.response.AnnotationViewResponseModel;
import com.vntana.core.rest.facade.annotation.builder.AnnotationViewModelBuilder;
import com.vntana.core.rest.facade.user.builder.UserModelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by Vardan Aivazian
 * Date: 16.09.2020
 * Time: 14:27
 */
@Component
public class AnnotationViewModelBuilderImpl implements AnnotationViewModelBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationViewModelBuilderImpl.class);

    private final UserModelBuilder userModelBuilder;

    public AnnotationViewModelBuilderImpl(final UserModelBuilder userModelBuilder) {
        this.userModelBuilder = userModelBuilder;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Override
    public AnnotationViewResponseModel build(final Annotation annotation) {
        LOGGER.debug("Building annotation view model for the provided annotation - {}", annotation);
        Assert.notNull(annotation, "The annotation should not be null");
        final AnnotationViewResponseModel model = new AnnotationViewResponseModel(
                annotation.getUuid(),
                annotation.getProductUuid(),
                annotation.getText(),
                userModelBuilder.build(annotation.getUser()),
                annotation.getNumber(),
                annotation.getResolved(),
                annotation.getD1(),
                annotation.getD2(),
                annotation.getD3(),
                annotation.getCreated(),
                annotation.getUpdated()
        );
        LOGGER.debug(
                "Successfully built annotation view model for the provided annotation - {}, result - {}",
                annotation,
                model
        );
        return model;
    }
}
