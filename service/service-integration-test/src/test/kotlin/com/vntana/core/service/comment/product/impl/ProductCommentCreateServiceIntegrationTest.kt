package com.vntana.core.service.comment.product.impl

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 04.09.2020
 * Time: 14:12
 */
class ProductCommentCreateServiceIntegrationTest : AbstractProductCommentServiceIntegrationTest() {

    @Test
    fun `test create`() {
        val dto = integrationTestHelper.buildProductCommentCreateDto(
                userUuid = userIntegrationTestHelper.persistUser().uuid,
                productUuid = uuid() 
        )
        productCommentService.create(dto).let {
            flushAndClear()
            assertThat(it.uuid).isNotBlank()
            assertThat(it.message).isEqualTo(dto.message)
            assertThat(it.user.uuid).isEqualTo(dto.userUuid)
            assertThat(it.productUuid).isEqualTo(dto.productUuid)
        }
    }
}