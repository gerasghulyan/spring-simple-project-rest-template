package com.vntana.core.rest.facade.comment.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.model.comment.CommentErrorResponseModel;
import com.vntana.core.model.comment.request.CreateProductCommentRequestModel;

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 3:14 PM
 */
interface ProductCommentFacadePreconditionChecker {

    SingleErrorWithStatus<CommentErrorResponseModel> checkCreateProductComment(final CreateProductCommentRequestModel request);
}
