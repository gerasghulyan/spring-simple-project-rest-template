package com.vntana.core.service.comment.product.impl

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.stream.Collectors

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
        val persistProductComment3 = integrationTestHelper.persistProductComment()

        integrationTestHelper.buildProductCommentFindByProductUuidDto(productUuid = persistProductComment1.productUuid).let { productCommentFindByProductUuidDto ->

            productCommentService.findByProductUuid(productCommentFindByProductUuidDto).run {
                assertThat(totalElements).isEqualTo(2)
                val list = get().collect(Collectors.toList())
                assertThat(list).containsExactlyInAnyOrder(persistProductComment1, persistProductComment2)
                assertThat(list).doesNotContain(persistProductComment3)
            }
        }
    }
}