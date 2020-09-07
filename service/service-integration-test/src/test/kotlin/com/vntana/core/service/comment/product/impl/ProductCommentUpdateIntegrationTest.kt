package com.vntana.core.service.comment.product.impl

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 04.09.2020
 * Time: 15:22
 */
class ProductCommentUpdateIntegrationTest : AbstractProductCommentServiceIntegrationTest() {

    @Test
    fun `test update`() {
        val productComment = integrationTestHelper.persistProductComment()
        val dto = integrationTestHelper.buildProductCommentUpdateDto(productComment.uuid)
        productCommentService.update(dto).let {
            flushAndClear()
            assertThat(it.uuid).isEqualTo(productComment.uuid)
        }
    }
}