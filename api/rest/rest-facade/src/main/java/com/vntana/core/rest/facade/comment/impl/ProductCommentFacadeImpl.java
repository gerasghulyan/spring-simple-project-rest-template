package com.vntana.core.rest.facade.comment.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.domain.comment.ProductComment;
import com.vntana.core.model.comment.CommentErrorResponseModel;
import com.vntana.core.model.comment.request.CreateProductCommentRequestModel;
import com.vntana.core.model.comment.request.FindProductCommentByFilterRequestModel;
import com.vntana.core.model.comment.response.CreateCommentResultResponse;
import com.vntana.core.model.comment.response.ProductCommentGridResponseModel;
import com.vntana.core.model.comment.response.ProductCommentViewResponseModel;
import com.vntana.core.model.comment.response.ProductCommentViewResultResponse;
import com.vntana.core.rest.facade.comment.ProductCommentFacade;
import com.vntana.core.rest.facade.comment.builder.ProductCommentViewModelBuilder;
import com.vntana.core.service.comment.product.ProductCommentService;
import com.vntana.core.service.comment.product.dto.ProductCommentCreateDto;
import com.vntana.core.service.comment.product.dto.ProductCommentFindByProductUuidDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 3:06 PM
 */
@Component
class ProductCommentFacadeImpl implements ProductCommentFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCommentFacadeImpl.class);

    private final ProductCommentFacadePreconditionChecker preconditionChecker;
    private final ProductCommentService productCommentService;
    private final ProductCommentViewModelBuilder productCommentViewModelBuilder;

    public ProductCommentFacadeImpl(final ProductCommentFacadePreconditionChecker preconditionChecker,
                                    final ProductCommentService productCommentService,
                                    final ProductCommentViewModelBuilder productCommentViewModelBuilder) {
        this.preconditionChecker = preconditionChecker;
        this.productCommentService = productCommentService;
        this.productCommentViewModelBuilder = productCommentViewModelBuilder;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Override
    public CreateCommentResultResponse create(final CreateProductCommentRequestModel request) {
        LOGGER.debug("Creating product comment for the provided request - {}", request);
        final SingleErrorWithStatus<CommentErrorResponseModel> error = preconditionChecker.checkCreateProductComment(request);
        if (error.isPresent()) {
            return new CreateCommentResultResponse(error.getHttpStatus(), error.getError());
        }
        final ProductComment comment = productCommentService.create(
                new ProductCommentCreateDto(request.getUserUuid(), request.getProductUuid(), request.getMessage())
        );
        LOGGER.debug("Successfully created product comment for the provided request - {}", request);
        return new CreateCommentResultResponse(comment.getUuid());
    }

    @Transactional(readOnly = true)
    @Override
    public ProductCommentViewResultResponse findByFilter(final FindProductCommentByFilterRequestModel request) {
        LOGGER.debug("Retrieving product comment for the provided request - {}", request);
        final Page<ProductComment> productComments = productCommentService.findByProductUuid(
                new ProductCommentFindByProductUuidDto(request.getPage(), request.getSize(), request.getProductUuid())
        );
        final List<ProductCommentViewResponseModel> viewModels = productComments.get()
                .map(productCommentViewModelBuilder::build)
                .collect(Collectors.toList());
        final ProductCommentGridResponseModel responseModel = new ProductCommentGridResponseModel(
                (int) productComments.getTotalElements(),
                viewModels
        );
        LOGGER.debug("Successfully retrieved product comment for the provided request - {}, result - {}", request, responseModel);
        return new ProductCommentViewResultResponse(responseModel);
    }
}
