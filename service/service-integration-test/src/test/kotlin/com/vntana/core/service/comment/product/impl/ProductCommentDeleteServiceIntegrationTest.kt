package com.vntana.core.service.comment.product.impl

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 04.09.2020
 * Time: 14:47
 */
class ProductCommentDeleteServiceIntegrationTest : AbstractProductCommentServiceIntegrationTest() {

    @Test
    fun `delete test`() {
        val productComment = integrationTestHelper.persistProductComment()
        productCommentService.delete(productComment.uuid).let {
            assertThat(it.removed).isNotNull()
        }
    }
}