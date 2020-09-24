package com.vntana.core.service.comment.product.impl

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 04.09.2020
 * Time: 15:04
 */
class ProductCommentFindByProductUuidIntegrationTest : AbstractProductCommentServiceIntegrationTest() {

    @Test
    fun `test findByProductUuid`() {
        val persistProductComment1 = integrationTestHelper.persistProductComment()
        val persistProductComment2 = integrationTestHelper.persistProductComment(productUuid = persistProductComment1.productUuid)
        val persistProductComment3 = integrationTestHelper.persistProductComment(productUuid = persistProductComment1.productUuid)
        flushAndClear()
        integrationTestHelper.buildProductCommentFindByProductUuidDto(productUuid = persistProductComment1.productUuid).let {
            productCommentService.findByProductUuid(it)
        }.let {
            assertThat(it.totalElements).isEqualTo(3)
            assertThat(it).containsExactly(persistProductComment3, persistProductComment2, persistProductComment1)
        }
    }
}