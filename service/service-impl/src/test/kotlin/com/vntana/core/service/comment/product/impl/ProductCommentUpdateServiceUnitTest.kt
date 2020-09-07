package com.vntana.core.service.comment.product.impl

import com.vntana.core.domain.comment.ProductComment
import org.apache.commons.lang3.StringUtils
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock
import org.junit.Test
import java.util.*

/**
 * Created by Vardan Aivazian
 * Date: 04.09.2020
 * Time: 12:52
 */
class ProductCommentUpdateServiceUnitTest  : AbstractProductCommentServiceUnitTest() {

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
        val uuid = uuid()
        val dto = commonTestHelper.buildProductCommentUpdateDto(uuid = uuid)
        val productComment = commonTestHelper.buildProductCommentWithUuid(uuid = uuid)
        EasyMock.expect(productCommentRepository.findByUuid(dto.uuid)).andReturn(Optional.of(productComment))
        EasyMock.expect(productCommentRepository.save(EasyMock.isA(ProductComment::class.java))).andAnswer { EasyMock.getCurrentArguments()[0] as ProductComment }
        replayAll()
        productCommentService.update(dto).let {
            assertThat(it.uuid).isEqualTo(dto.uuid)
        }
        verifyAll()
    }
    
}