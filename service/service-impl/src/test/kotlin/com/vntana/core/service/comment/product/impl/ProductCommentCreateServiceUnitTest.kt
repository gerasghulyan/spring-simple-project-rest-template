package com.vntana.core.service.comment.product.impl

import com.vntana.core.domain.comment.ProductComment
import org.apache.commons.lang3.StringUtils
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 03.09.2020
 * Time: 18:16
 */
class ProductCommentCreateServiceUnitTest : AbstractProductCommentServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { commonTestHelper.buildProductCommentCreateDto(message = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildProductCommentCreateDto(message = StringUtils.EMPTY) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildProductCommentCreateDto(userUuid = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildProductCommentCreateDto(userUuid = StringUtils.EMPTY) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildProductCommentCreateDto(productUuid = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildProductCommentCreateDto(productUuid = StringUtils.EMPTY) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { productCommentService.create(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val user = userCommonTestHelper.buildUser()
        val dto = commonTestHelper.buildProductCommentCreateDto(userUuid = user.uuid)
        EasyMock.expect(userService.getByUuid(dto.userUuid)).andReturn(user)
        EasyMock.expect(productCommentRepository.save(EasyMock.isA(ProductComment::class.java))).andAnswer { EasyMock.getCurrentArguments()[0] as ProductComment }
        replayAll()
        productCommentService.create(dto).let {
            assertThat(it.user.uuid).isEqualTo(dto.userUuid)
            assertThat(it.productUuid).isEqualTo(dto.productUuid)
        }
        verifyAll()
    }
    
}