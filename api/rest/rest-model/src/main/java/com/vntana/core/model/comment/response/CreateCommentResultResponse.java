package com.vntana.core.model.comment.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.comment.CommentErrorResponseModel;

import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 3:08 PM
 */
public class CreateCommentResultResponse extends AbstractResultResponseModel<CreateCommentResponseModel, CommentErrorResponseModel> {

    public CreateCommentResultResponse() {
        super();
    }

    public CreateCommentResultResponse(final int httpStatusCode, final CommentErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public CreateCommentResultResponse(final int httpStatusCode, final List<CommentErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public CreateCommentResultResponse(final CreateCommentResponseModel responseModel) {
        super(responseModel);
    }
}
