package com.vntana.core.rest.facade.comment.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.model.comment.CommentErrorResponseModel;
import com.vntana.core.model.comment.request.CreateProductCommentRequestModel;
import com.vntana.core.model.comment.request.DeleteProductCommentRequestModel;
import com.vntana.core.model.comment.request.UpdateProductCommentRequestModel;

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 3:14 PM
 */
interface ProductCommentFacadePreconditionChecker {

    SingleErrorWithStatus<CommentErrorResponseModel> checkCreateProductComment(final CreateProductCommentRequestModel request);

    SingleErrorWithStatus<CommentErrorResponseModel> checkUpdateProductComment(final UpdateProductCommentRequestModel request);

    SingleErrorWithStatus<CommentErrorResponseModel> checkDeleteProductComment(final DeleteProductCommentRequestModel request);
}
