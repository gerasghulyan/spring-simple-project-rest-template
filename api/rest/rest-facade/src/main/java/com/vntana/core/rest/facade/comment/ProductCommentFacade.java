package com.vntana.core.rest.facade.comment;

import com.vntana.core.model.comment.request.CreateProductCommentRequestModel;
import com.vntana.core.model.comment.request.FindProductCommentByFilterRequestModel;
import com.vntana.core.model.comment.response.CreateCommentResultResponse;
import com.vntana.core.model.comment.response.ProductCommentViewResultResponse;

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 3:06 PM
 */
public interface ProductCommentFacade {

    CreateCommentResultResponse create(final CreateProductCommentRequestModel request);

    ProductCommentViewResultResponse findByFilter(final FindProductCommentByFilterRequestModel request);
}
