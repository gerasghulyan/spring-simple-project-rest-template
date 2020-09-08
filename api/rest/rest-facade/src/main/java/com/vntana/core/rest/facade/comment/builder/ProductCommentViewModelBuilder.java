package com.vntana.core.rest.facade.comment.builder;

import com.vntana.core.domain.comment.ProductComment;
import com.vntana.core.model.comment.response.ProductCommentViewResponseModel;

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 3:36 PM
 */
public interface ProductCommentViewModelBuilder {

    ProductCommentViewResponseModel build(final ProductComment comment);
}
