package com.vntana.core.service.comment.product.impl

import org.apache.commons.lang3.StringUtils
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock
import org.junit.Test
import org.springframework.data.domain.PageRequest

/**
 * Created by Vardan Aivazian
 * Date: 03.09.2020
 * Time: 19:59
 */
class ProductCommentFindByProductUuidUnitTest : AbstractProductCommentServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { commonTestHelper.buildProductCommentFindByProductUuidDto(productUuid = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildProductCommentCreateDto(productUuid = StringUtils.EMPTY) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val page = 0
        val size = 5
        val productUuid = uuid()
        val productCommentPage = commonTestHelper.buildProductCommentPage(productUuid = productUuid)
        val dto = commonTestHelper.buildProductCommentFindByProductUuidDto(page = page, size = size, productUuid = productUuid)
        EasyMock.expect(productCommentRepository.findByProductUuidAndRemovedIsNull(productUuid, PageRequest.of(page, size))).andReturn(productCommentPage)
        replayAll()
        assertThat(productCommentService.findByProductUuid(dto)).isEqualTo(productCommentPage)
        verifyAll()
    }
}