package com.vntana.core.model.comment.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.comment.CommentErrorResponseModel;

import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 3:25 PM
 */
public class ProductCommentViewResultResponse extends AbstractResultResponseModel<ProductCommentGridResponseModel, CommentErrorResponseModel> {

    public ProductCommentViewResultResponse() {
        super();
    }

    public ProductCommentViewResultResponse(final int httpStatusCode, final CommentErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public ProductCommentViewResultResponse(final int httpStatusCode, final List<CommentErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public ProductCommentViewResultResponse(final ProductCommentGridResponseModel response) {
        super(response);
    }
}
