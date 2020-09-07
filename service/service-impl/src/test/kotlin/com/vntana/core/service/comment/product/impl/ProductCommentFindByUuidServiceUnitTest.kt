package com.vntana.core.service.comment.product.impl

import com.vntana.core.service.comment.product.exception.ProductCommentNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock
import org.junit.Test
import java.util.*

/**
 * Created by Vardan Aivazian
 * Date: 04.09.2020
 * Time: 13:19
 */
class ProductCommentFindByUuidServiceUnitTest : AbstractProductCommentServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { productCommentService.findByUuid(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { productCommentService.findByUuid(emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when comment not found`() {
        val uuid = uuid()
        resetAll()
        EasyMock.expect(productCommentRepository.findByUuid(uuid)).andReturn(Optional.empty())
        replayAll()

        assertThatThrownBy { productCommentService.findByUuid(uuid) }
                .isExactlyInstanceOf(ProductCommentNotFoundException::class.java)
        verifyAll()
    }

    @Test
    fun `test when found`() {
        val uuid = uuid()
        val productComment = commonTestHelper.buildProductCommentWithUuid(uuid = uuid)
        resetAll()
        EasyMock.expect(productCommentRepository.findByUuid(uuid)).andReturn(Optional.of(productComment))
        replayAll()
        productCommentService.findByUuid(uuid).let {
            assertThat(it.uuid).isEqualTo(uuid)
        }
        verifyAll()
    }
}