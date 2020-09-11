package com.vntana.core.rest.resource.comment;

import com.vntana.commons.web.utils.ResponseEntityUtils;
import com.vntana.core.model.comment.request.CreateProductCommentRequestModel;
import com.vntana.core.model.comment.request.DeleteProductCommentRequestModel;
import com.vntana.core.model.comment.request.FindProductCommentByFilterRequestModel;
import com.vntana.core.model.comment.request.UpdateProductCommentRequestModel;
import com.vntana.core.model.comment.response.CreateCommentResultResponse;
import com.vntana.core.model.comment.response.DeleteCommentResultResponse;
import com.vntana.core.model.comment.response.ProductCommentViewResultResponse;
import com.vntana.core.model.comment.response.UpdateCommentResultResponse;
import com.vntana.core.rest.facade.comment.ProductCommentFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 4:53 PM
 */
@RestController
@RequestMapping(path = "/product-comments", produces = "application/json")
public class ProductCommentResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCommentResource.class);

    private final ProductCommentFacade productCommentFacade;

    public ProductCommentResource(final ProductCommentFacade productCommentFacade) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.productCommentFacade = productCommentFacade;
    }

    @PostMapping
    public ResponseEntity<CreateCommentResultResponse> create(@RequestBody final CreateProductCommentRequestModel request) {
        LOGGER.debug("Processing product comment resource create methods for the provided request - {}", request);
        final CreateCommentResultResponse resultResponse = productCommentFacade.create(request);
        LOGGER.debug(
                "Successfully processed product comment resource create methods for the provided request - {}, result - {}",
                request,
                resultResponse
        );
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }

    @PutMapping
    public ResponseEntity<UpdateCommentResultResponse> update(@RequestBody final UpdateProductCommentRequestModel request) {
        LOGGER.debug("Processing product comment resource update methods for the provided request - {}", request);
        UpdateCommentResultResponse resultResponse = productCommentFacade.update(request);
        LOGGER.debug(
                "Successfully processed product comment resource update methods for the provided request - {}, result - {}",
                request,
                resultResponse
        );
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }

    @DeleteMapping
    public ResponseEntity<DeleteCommentResultResponse> delete(@RequestBody final DeleteProductCommentRequestModel request) {
        LOGGER.debug("Processing product comment resource delete methods for the provided request - {}", request);
        DeleteCommentResultResponse resultResponse = productCommentFacade.delete(request);
        LOGGER.debug(
                "Successfully processed product comment resource delete methods for the provided request - {}, result - {}",
                request,
                resultResponse
        );
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }

    @PostMapping("/search")
    public ResponseEntity<ProductCommentViewResultResponse> search(@RequestBody final FindProductCommentByFilterRequestModel request) {
        LOGGER.debug("Processing product comment resource create methods for the provided request - {}", request);
        final ProductCommentViewResultResponse resultResponse = productCommentFacade.findByFilter(request);
        LOGGER.debug(
                "Successfully processed product comment resource create methods for the provided request - {}, result - {}",
                request,
                resultResponse
        );
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }
}
