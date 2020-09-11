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

    public UpdateCommentResultResponse(int httpStatusCode, CommentErrorResponseModel error) {
        super(httpStatusCode, error);
    }

    public UpdateCommentResultResponse(int httpStatusCode, List<CommentErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public UpdateCommentResultResponse(UpdateCommentResponseModel updateCommentResponseModel) {
        super(updateCommentResponseModel);
    }
}
