package com.vntana.core.service.comment.product.impl

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 04.09.2020
 * Time: 14:59
 */
class ProductCommentExistByUuidServiceIntegrationTest : AbstractProductCommentServiceIntegrationTest() {

    @Test
    fun `test when is not found`() {
        assertThat(productCommentService.existsByUuid(uuid())).isFalse()
    }

    @Test
    fun `test when found`() {
        val productComment = integrationTestHelper.persistProductComment()
        assertThat(productCommentService.existsByUuid(productComment.uuid)).isTrue()
    }
}