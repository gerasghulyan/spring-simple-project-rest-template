package com.vntana.core.rest.facade.annotation.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.domain.annotation.Annotation;
import com.vntana.core.model.annotation.AnnotationErrorResponseModel;
import com.vntana.core.model.annotation.request.CreateAnnotationRequestModel;
import com.vntana.core.model.annotation.request.DeleteAnnotationRequestModel;
import com.vntana.core.model.annotation.request.FindAnnotationByFilterRequestModel;
import com.vntana.core.model.annotation.request.UpdateAnnotationRequestModel;
import com.vntana.core.model.annotation.response.*;
import com.vntana.core.rest.facade.annotation.AnnotationFacade;
import com.vntana.core.rest.facade.annotation.builder.AnnotationViewModelBuilder;
import com.vntana.core.service.annotation.AnnotationService;
import com.vntana.core.service.annotation.dto.AnnotationCreateDto;
import com.vntana.core.service.annotation.dto.AnnotationFindByProductUuidDto;
import com.vntana.core.service.annotation.dto.AnnotationUpdateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Vardan Aivazian
 * Date: 15.09.2020
 * Time: 18:04
 */
@Component
public class AnnotationFacadeImpl implements AnnotationFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationFacadeImpl.class);

    private final AnnotationFacadePreconditionChecker preconditionChecker;
    private final AnnotationService annotationService;
    private final AnnotationViewModelBuilder annotationViewModelBuilder;

    public AnnotationFacadeImpl(final AnnotationFacadePreconditionChecker preconditionChecker, final AnnotationService annotationService, final AnnotationViewModelBuilder annotationViewModelBuilder) {
        this.preconditionChecker = preconditionChecker;
        this.annotationService = annotationService;
        this.annotationViewModelBuilder = annotationViewModelBuilder;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Transactional
    @Override
    public CreateAnnotationResultResponse create(final CreateAnnotationRequestModel request) {
        LOGGER.debug("Creating annotation for the provided request - {}", request);
        final SingleErrorWithStatus<AnnotationErrorResponseModel> error = preconditionChecker.checkCreateAnnotation(request);
        if (error.isPresent()) {
            return new CreateAnnotationResultResponse(error.getHttpStatus(), error.getError());
        }
        final Annotation annotation = annotationService.create(
                new AnnotationCreateDto(request.getUserUuid(), request.getProductUuid(), request.getText(), request.getNumber(), request.getD1(), request.getD2(), request.getD3())
        );
        LOGGER.debug("Successfully created annotation for the provided request - {}", request);
        return new CreateAnnotationResultResponse(annotation.getUuid());
    }

    @Transactional
    @Override
    public UpdateAnnotationResultResponse update(final UpdateAnnotationRequestModel request) {
        LOGGER.debug("Updating annotation for the provided request - {}", request);
        final SingleErrorWithStatus<AnnotationErrorResponseModel> error = preconditionChecker.checkUpdateAnnotation(request);
        if (error.isPresent()) {
            return new UpdateAnnotationResultResponse(error.getHttpStatus(), error.getError());
        }
        final Annotation annotation = annotationService.update(
                new AnnotationUpdateDto(request.getUuid(), request.getText(), request.getD1(), request.getD2(), request.getD3())
        );

        LOGGER.debug("Successfully updated annotation for the provided request - {}", request);
        final UpdateAnnotationResponseModel responseModel = new UpdateAnnotationResponseModel(annotation.getUuid(), annotation.getText(), annotation.getD1(), annotation.getD2(), annotation.getD3());
        return new UpdateAnnotationResultResponse(responseModel);
    }

    @Transactional
    @Override
    public DeleteAnnotationResultResponse delete(final DeleteAnnotationRequestModel request) {
        LOGGER.debug("Deleting annotation for the provided request - {}", request);
        final SingleErrorWithStatus<AnnotationErrorResponseModel> error = preconditionChecker.checkDeleteAnnotation(request);
        if (error.isPresent()) {
            return new DeleteAnnotationResultResponse(error.getHttpStatus(), error.getError());
        }
        final Annotation annotation = annotationService.delete(request.getUuid());
        LOGGER.debug("Successfully deleted annotation for the provided request - {}", request);
        return new DeleteAnnotationResultResponse(annotation.getUuid());
    }

    @Transactional(readOnly = true)
    @Override
    public AnnotationViewResultResponse findByFilter(final FindAnnotationByFilterRequestModel request) {
        LOGGER.debug("Retrieving annotation for the provided request - {}", request);
        final Page<Annotation> annotations = annotationService.findByProductUuid(
                new AnnotationFindByProductUuidDto(request.getPage(), request.getSize(), request.getProductUuid())
        );
        final List<AnnotationViewResponseModel> viewModels = annotations.get()
                .map(annotationViewModelBuilder::build)
                .collect(Collectors.toList());

        final AnnotationGridResponseModel responseModel = new AnnotationGridResponseModel(
                (int) annotations.getTotalElements(),
                viewModels
        );
        LOGGER.debug("Successfully retrieved annotation for the provided request - {}", request);
        return new AnnotationViewResultResponse(responseModel);
    }
}
