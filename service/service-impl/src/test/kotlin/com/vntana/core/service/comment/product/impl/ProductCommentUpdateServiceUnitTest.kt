package com.vntana.core.service.comment.product.impl

import com.vntana.core.domain.comment.ProductComment
import org.apache.commons.lang3.StringUtils
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.*
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 04.09.2020
 * Time: 12:52
 */
class ProductCommentUpdateServiceUnitTest : AbstractProductCommentServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { commonTestHelper.buildProductCommentUpdateDto(uuid = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildProductCommentUpdateDto(uuid = StringUtils.EMPTY) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildProductCommentUpdateDto(message = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildProductCommentUpdateDto(message = StringUtils.EMPTY) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { productCommentService.update(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val productComment = commonTestHelper.buildProductComment()
        val dto = commonTestHelper.buildProductCommentUpdateDto(productComment.uuid)
        expect(commentService.findByUuid(dto.uuid)).andReturn(productComment)
        expect(productCommentRepository.save(isA(ProductComment::class.java))).andAnswer { getCurrentArguments()[0] as ProductComment }
        replayAll()
        productCommentService.update(dto).let {
            assertThat(it.uuid).isEqualTo(dto.uuid)
            assertThat(it.message).isEqualTo(dto.message)
        }
        verifyAll()
    }
}