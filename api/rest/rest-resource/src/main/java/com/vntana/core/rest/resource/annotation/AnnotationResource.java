package com.vntana.core.rest.resource.annotation;

import com.vntana.commons.web.utils.ResponseEntityUtils;
import com.vntana.core.model.annotation.request.CreateAnnotationRequestModel;
import com.vntana.core.model.annotation.request.DeleteAnnotationRequestModel;
import com.vntana.core.model.annotation.request.FindAnnotationByFilterRequestModel;
import com.vntana.core.model.annotation.request.UpdateAnnotationRequestModel;
import com.vntana.core.model.annotation.response.AnnotationViewResultResponse;
import com.vntana.core.model.annotation.response.CreateAnnotationResultResponse;
import com.vntana.core.model.annotation.response.DeleteAnnotationResultResponse;
import com.vntana.core.model.annotation.response.UpdateAnnotationResultResponse;
import com.vntana.core.rest.facade.annotation.AnnotationFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Vardan Aivazian
 * Date: 16.09.2020
 * Time: 17:49
 */
@RestController
@RequestMapping(path = "/annotations", produces = "application/json")
public class AnnotationResource {


    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationResource.class);

    private final AnnotationFacade annotationFacade;

    public AnnotationResource(final AnnotationFacade annotationFacade) {
        this.annotationFacade = annotationFacade;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }
    
    @PostMapping
    public ResponseEntity<CreateAnnotationResultResponse> create(@RequestBody CreateAnnotationRequestModel request) {
        LOGGER.debug("Processing annotation resource create methods for the provided request - {}", request);
        final CreateAnnotationResultResponse resultResponse = annotationFacade.create(request);
        LOGGER.debug(
                "Successfully processed annotation resource create methods for the provided request - {}, result - {}",
                request,
                resultResponse
        );
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }
    
    @PutMapping
    public ResponseEntity<UpdateAnnotationResultResponse> update(@RequestBody UpdateAnnotationRequestModel request) {
        LOGGER.debug("Processing annotation resource update methods for the provided request - {}", request);
        final UpdateAnnotationResultResponse resultResponse = annotationFacade.update(request);
        LOGGER.debug(
                "Successfully processed annotation resource update methods for the provided request - {}, result - {}",
                request,
                resultResponse
        );
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }
    
    @DeleteMapping
    public ResponseEntity<DeleteAnnotationResultResponse> delete(@RequestBody DeleteAnnotationRequestModel request) {
        LOGGER.debug("Processing annotation resource delete methods for the provided request - {}", request);
        final DeleteAnnotationResultResponse resultResponse = annotationFacade.delete(request);
        LOGGER.debug(
                "Successfully processed annotation resource delete methods for the provided request - {}, result - {}",
                request,
                resultResponse
        );
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }

    @PostMapping("/search")
    public ResponseEntity<AnnotationViewResultResponse> search(@RequestBody final FindAnnotationByFilterRequestModel request) {
        LOGGER.debug("Processing annotation resource create methods for the provided request - {}", request);
        final AnnotationViewResultResponse resultResponse = annotationFacade.findByFilter(request);
        LOGGER.debug(
                "Successfully processed annotation resource create methods for the provided request - {}, result - {}",
                request,
                resultResponse
        );
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }
}
