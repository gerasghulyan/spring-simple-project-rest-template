package com.vntana.core.rest.facade.annotation.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.domain.annotation.Annotation;
import com.vntana.core.model.annotation.AnnotationErrorResponseModel;
import com.vntana.core.model.annotation.request.CreateAnnotationRequestModel;
import com.vntana.core.model.annotation.request.DeleteAnnotationRequestModel;
import com.vntana.core.model.annotation.request.UpdateAnnotationRequestModel;
import com.vntana.core.service.annotation.AnnotationService;
import com.vntana.core.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;

/**
 * Created by Vardan Aivazian
 * Date: 16.09.2020
 * Time: 12:35
 */
@Component
public class AnnotationFacadePreconditionCheckerImpl implements AnnotationFacadePreconditionChecker {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationFacadePreconditionCheckerImpl.class);

    private final UserService userService;
    private final AnnotationService annotationService;

    public AnnotationFacadePreconditionCheckerImpl(final UserService userService, final AnnotationService annotationService) {
        this.userService = userService;
        this.annotationService = annotationService;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Override
    public SingleErrorWithStatus<AnnotationErrorResponseModel> checkCreateAnnotation(final CreateAnnotationRequestModel request) {
        LOGGER.debug("Checking annotation creation for possible errors for the provided request - {}", request);
        if (!userService.existsByUuid(request.getUserUuid())) {
            return SingleErrorWithStatus.of(SC_NOT_FOUND, AnnotationErrorResponseModel.USER_NOT_FOUND);
        }
        LOGGER.debug("Successfully checked annotation creation for possible errors for the provided request - {}", request);
        return SingleErrorWithStatus.empty();
    }

    @Override
    public SingleErrorWithStatus<AnnotationErrorResponseModel> checkUpdateAnnotation(final UpdateAnnotationRequestModel request) {
        LOGGER.debug("Checking annotation update for possible errors for the provided request - {}", request);
        final Optional<Annotation> optionalAnnotation = annotationService.findByUuid(request.getUuid());
        if (!optionalAnnotation.isPresent()) {
            return SingleErrorWithStatus.of(SC_NOT_FOUND, AnnotationErrorResponseModel.ANNOTATION_NOT_FOUND);
        }
        final Annotation annotation = optionalAnnotation.get();
        if (!annotation.getUser().getUuid().equals(request.getUserUuid())) {
            return SingleErrorWithStatus.of(SC_FORBIDDEN, AnnotationErrorResponseModel.USER_ACCESS_DENIED);
        }
        LOGGER.debug("Successfully checked annotation update for possible errors for the provided request - {}", request);
        return SingleErrorWithStatus.empty();
    }

    @Override
    public SingleErrorWithStatus<AnnotationErrorResponseModel> checkDeleteAnnotation(final DeleteAnnotationRequestModel request) {
        LOGGER.debug("Checking annotation delete for possible errors for the provided request - {}", request);
        final Optional<Annotation> optionalAnnotation = annotationService.findByUuid(request.getUuid());
        if (!optionalAnnotation.isPresent()) {
            return SingleErrorWithStatus.of(SC_NOT_FOUND, AnnotationErrorResponseModel.ANNOTATION_NOT_FOUND);
        }
        final Annotation annotation = optionalAnnotation.get();
        if (!annotation.getUser().getUuid().equals(request.getUserUuid())) {
            return SingleErrorWithStatus.of(SC_FORBIDDEN, AnnotationErrorResponseModel.USER_ACCESS_DENIED);
        }
        LOGGER.debug("Successfully checked annotation delete for possible errors for the provided request - {}", request);
        return SingleErrorWithStatus.empty();
    }
}
