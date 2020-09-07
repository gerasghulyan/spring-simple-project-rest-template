package com.vntana.core.service.comment.product.impl

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 04.09.2020
 * Time: 15:29
 */
class ProductCommentFindByUuidIntegrationTest : AbstractProductCommentServiceIntegrationTest() {

    @Test
    fun `test findByUuid`() {

                integrationTestHelper.persistProductComment().let { productComment ->

            productCommentService.findByUuid(productComment.uuid).let {
                assertThat(it.uuid).isEqualTo(productComment.uuid)
            }
        }
    }
}