package com.vntana.core.model.comment.response;

import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 3:08 PM
 */
public class CreateCommentResponseModel extends AbstractUuidAwareResponseModel {
    
    public CreateCommentResponseModel() {
        super();
    }

    public CreateCommentResponseModel(final String uuid) {
        super(uuid);
    }
}
