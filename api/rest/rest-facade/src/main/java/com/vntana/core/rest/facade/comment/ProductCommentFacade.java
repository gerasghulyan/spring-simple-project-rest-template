package com.vntana.core.rest.facade.comment;

import com.vntana.core.model.comment.request.CreateProductCommentRequestModel;
import com.vntana.core.model.comment.request.DeleteProductCommentRequestModel;
import com.vntana.core.model.comment.request.FindProductCommentByFilterRequestModel;
import com.vntana.core.model.comment.request.UpdateProductCommentRequestModel;
import com.vntana.core.model.comment.response.CreateCommentResultResponse;
import com.vntana.core.model.comment.response.DeleteCommentResultResponse;
import com.vntana.core.model.comment.response.ProductCommentViewResultResponse;
import com.vntana.core.model.comment.response.UpdateCommentResultResponse;

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 3:06 PM
 */
public interface ProductCommentFacade {

    CreateCommentResultResponse create(final CreateProductCommentRequestModel request);

    UpdateCommentResultResponse update(final UpdateProductCommentRequestModel request);

    DeleteCommentResultResponse delete(final DeleteProductCommentRequestModel request);

    ProductCommentViewResultResponse findByFilter(final FindProductCommentByFilterRequestModel request);
}
