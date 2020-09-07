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
        integrationTestHelper.persistProductComment().let { productComment ->
            
            integrationTestHelper.buildProductCommentFindByProductUuidDto(productUuid = productComment.productUuid).let { productCommentFindByProductUuidDto ->

                productCommentService.findByProductUuid(productCommentFindByProductUuidDto).let {
                    assertThat(it).isNotEmpty
                    assertThat(it).containsOnly(productComment)
                }
            }
        }
    }
}