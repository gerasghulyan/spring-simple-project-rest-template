package com.vntana.core.service.comment.product.impl

import com.vntana.core.helper.integration.comment.product.ProductCommentIntegrationTestHelper
import com.vntana.core.helper.integration.user.UserIntegrationTestHelper
import com.vntana.core.service.AbstractServiceIntegrationTest
import com.vntana.core.service.comment.product.ProductCommentService
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Vardan Aivazian
 * Date: 04.09.2020
 * Time: 14:13
 */
abstract class AbstractProductCommentServiceIntegrationTest : AbstractServiceIntegrationTest() {
    
    @Autowired
    protected lateinit var productCommentService: ProductCommentService

    @Autowired
    protected lateinit var integrationTestHelper: ProductCommentIntegrationTestHelper

    @Autowired
    protected lateinit var userIntegrationTestHelper: UserIntegrationTestHelper
}