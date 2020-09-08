package com.vntana.core.rest.facade.comment.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.model.comment.CommentErrorResponseModel;
import com.vntana.core.model.comment.request.CreateProductCommentRequestModel;
import com.vntana.core.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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

    ProductCommentFacadePreconditionCheckerImpl(final UserService userService) {
        this.userService = userService;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Override
    public SingleErrorWithStatus<CommentErrorResponseModel> checkCreateProductComment(final CreateProductCommentRequestModel request) {
        LOGGER.debug("Checking product comment creation for possible errors  for the provided request - {}", request);
        if (!userService.existsByUuid(request.getUserUuid())) {
            return SingleErrorWithStatus.of(SC_NOT_FOUND, CommentErrorResponseModel.USER_NOT_FOUND);
        }
        LOGGER.debug("Successfully checked product comment creation for possible errors  for the provided request - {}", request);
        return SingleErrorWithStatus.empty();
    }
}
