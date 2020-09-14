package com.vntana.core.rest.resource.comment

import com.vntana.core.helper.comment.ProductCommentResourceTestHelper
import com.vntana.core.helper.user.UserResourceTestHelper
import com.vntana.core.rest.client.comment.ProductCommentResourceClient
import com.vntana.core.rest.resource.AbstractWebIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 5:08 PM
 */
abstract class AbstractProductCommentWebTest : AbstractWebIntegrationTest() {

    @Autowired
    protected lateinit var productCommentResourceClient: ProductCommentResourceClient

    @Autowired
    protected lateinit var resourceTestHelper: ProductCommentResourceTestHelper

    @Autowired
    protected lateinit var userResourceTestHelper: UserResourceTestHelper
}