package com.vntana.core.model.comment.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.comment.CommentErrorResponseModel;

import java.util.List;

/**
 * Created by Vardan Aivazian
 * Date: 10.09.2020
 * Time: 15:27
 **/
public class UpdateCommentResultResponse extends AbstractResultResponseModel<UpdateCommentResponseModel, CommentErrorResponseModel> {

    public UpdateCommentResultResponse() {
        super();
    }

    public UpdateCommentResultResponse(final int httpStatusCode, final CommentErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public UpdateCommentResultResponse(final int httpStatusCode, final List<CommentErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public UpdateCommentResultResponse(final UpdateCommentResponseModel updateCommentResponseModel) {
        super(updateCommentResponseModel);
    }
}
