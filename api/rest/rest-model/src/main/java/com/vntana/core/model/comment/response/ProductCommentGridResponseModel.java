package com.vntana.core.model.comment.response;

import com.vntana.commons.api.model.response.impl.AbstractGridResponseModel;

import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 3:30 PM
 */
public class ProductCommentGridResponseModel extends AbstractGridResponseModel<ProductCommentViewResponseModel> {

    public ProductCommentGridResponseModel() {
        super();
    }

    public ProductCommentGridResponseModel(final int totalCount, final List<ProductCommentViewResponseModel> items) {
        super(totalCount, items);
    }
}
