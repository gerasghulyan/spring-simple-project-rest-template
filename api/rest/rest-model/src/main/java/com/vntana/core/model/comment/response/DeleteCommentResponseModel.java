package com.vntana.core.model.comment.response;

import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;

/**
 * Created by Vardan Aivazian
 * Date: 10.09.2020
 * Time: 15:35
 **/
public class DeleteCommentResponseModel extends AbstractUuidAwareResponseModel {

    public DeleteCommentResponseModel() {
        super();
    }

    public DeleteCommentResponseModel(final String uuid) {
        super(uuid);
    }
}
