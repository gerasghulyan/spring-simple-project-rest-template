package com.vntana.core.service.comment.product.impl

import com.vntana.core.domain.comment.ProductComment
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock
import org.junit.Test
import java.util.*

/**
 * Created by Vardan Aivazian
 * Date: 04.09.2020
 * Time: 13:48
 */
class ProductCommentDeleteServiceUnitTest : AbstractProductCommentServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { productCommentService.delete(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { productCommentService.delete(emptyString()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test delete when found`() {
        val uuid = uuid()
        val productComment = commonTestHelper.buildProductCommentWithUuid(uuid)
        resetAll()
        EasyMock.expect(productCommentRepository.findByUuid(uuid)).andReturn(Optional.of(productComment))
        EasyMock.expect(productCommentRepository.save(EasyMock.isA(ProductComment::class.java))).andAnswer { EasyMock.getCurrentArguments()[0] as ProductComment }
        replayAll()
        productCommentService.delete(uuid).let {
            assertThat(it.removed).isNotNull()
        }
        verifyAll()
    }
}