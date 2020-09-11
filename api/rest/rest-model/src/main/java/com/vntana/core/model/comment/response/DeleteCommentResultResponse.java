package com.vntana.core.model.comment.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.comment.CommentErrorResponseModel;

import java.util.List;

/**
 * Created by Vardan Aivazian
 * Date: 10.09.2020
 * Time: 15:35
 **/
public class DeleteCommentResultResponse extends AbstractResultResponseModel<DeleteCommentResponseModel, CommentErrorResponseModel> {

    public DeleteCommentResultResponse() {
        super();
    }

    public DeleteCommentResultResponse(final int httpStatusCode, final CommentErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public DeleteCommentResultResponse(final int httpStatusCode, final List<CommentErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public DeleteCommentResultResponse(final String uuid) {
        super(new DeleteCommentResponseModel(uuid));
    }
}
