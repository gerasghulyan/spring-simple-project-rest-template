package com.vntana.core.rest.facade.comment.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.domain.comment.AbstractComment;
import com.vntana.core.model.comment.CommentErrorResponseModel;
import com.vntana.core.model.comment.request.CreateProductCommentRequestModel;
import com.vntana.core.model.comment.request.DeleteProductCommentRequestModel;
import com.vntana.core.model.comment.request.UpdateProductCommentRequestModel;
import com.vntana.core.service.comment.CommentService;
import com.vntana.core.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 3:15 PM
 */
@Component
class ProductCommentFacadePreconditionCheckerImpl implements ProductCommentFacadePreconditionChecker {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCommentFacadePreconditionCheckerImpl.class);

    private final UserService userService;
    private final CommentService commentService;

    ProductCommentFacadePreconditionCheckerImpl(final UserService userService, final CommentService commentService) {
        this.userService = userService;
        this.commentService = commentService;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Override
    public SingleErrorWithStatus<CommentErrorResponseModel> checkCreateProductComment(final CreateProductCommentRequestModel request) {
        LOGGER.debug("Checking product comment creation for possible errors for the provided request - {}", request);
        if (!userService.existsByUuid(request.getUserUuid())) {
            return SingleErrorWithStatus.of(SC_NOT_FOUND, CommentErrorResponseModel.USER_NOT_FOUND);
        }
        LOGGER.debug("Successfully checked product comment creation for possible errors for the provided request - {}", request);
        return SingleErrorWithStatus.empty();
    }

    @Override
    public SingleErrorWithStatus<CommentErrorResponseModel> checkUpdateProductComment(final UpdateProductCommentRequestModel request) {
        LOGGER.debug("Checking product comment update for possible errors for the provided request - {}", request);
        final Optional<AbstractComment> optionalComment = commentService.findByUuid(request.getUuid());
        if (!optionalComment.isPresent()) {
            return SingleErrorWithStatus.of(SC_NOT_FOUND, CommentErrorResponseModel.COMMENT_NOT_FOUND);
        }
        final AbstractComment comment = optionalComment.get();
        if (!comment.getUser().getUuid().equals(request.getUserUuid())) {
            return SingleErrorWithStatus.of(SC_FORBIDDEN, CommentErrorResponseModel.USER_ACCESS_DENIED);
        }
        LOGGER.debug("Successfully checked product comment update for possible errors for the provided request - {}", request);
        return SingleErrorWithStatus.empty();
    }

    @Override
    public SingleErrorWithStatus<CommentErrorResponseModel> checkDeleteProductComment(final DeleteProductCommentRequestModel request) {
        LOGGER.debug("Checking product comment delete for possible errors for the provided request - {}", request);
        final Optional<AbstractComment> optionalComment = commentService.findByUuid(request.getUuid());
        if (!optionalComment.isPresent()) {
            return SingleErrorWithStatus.of(SC_NOT_FOUND, CommentErrorResponseModel.COMMENT_NOT_FOUND);
        }
        final AbstractComment comment = optionalComment.get();
        if (!comment.getUser().getUuid().equals(request.getUserUuid())) {
            return SingleErrorWithStatus.of(SC_FORBIDDEN, CommentErrorResponseModel.USER_ACCESS_DENIED);
        }
        LOGGER.debug("Successfully checked product comment delete for possible errors for the provided request - {}", request);
        return SingleErrorWithStatus.empty();
    }
}
