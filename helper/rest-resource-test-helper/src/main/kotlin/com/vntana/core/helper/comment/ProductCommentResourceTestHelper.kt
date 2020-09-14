package com.vntana.core.helper.comment

import com.vntana.core.helper.user.UserResourceTestHelper
import com.vntana.core.rest.client.comment.ProductCommentResourceClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 5:10 PM
 */
@Component
class ProductCommentResourceTestHelper : ProductCommentRestTestHelper() {

    @Autowired
    private lateinit var productCommentResourceClient: ProductCommentResourceClient

    @Autowired
    private lateinit var userResourceTestHelper: UserResourceTestHelper

    fun persistProductComment(userUuid: String = userResourceTestHelper.persistUser().response().uuid,
                              productUuid: String = uuid(),
                              message: String = uuid()
    ) = buildCreateProductCommentRequestModel(userUuid = userUuid, productUuid = productUuid, message = message)
            .let { productCommentResourceClient.create(it) }
            .body!!.response().uuid
}