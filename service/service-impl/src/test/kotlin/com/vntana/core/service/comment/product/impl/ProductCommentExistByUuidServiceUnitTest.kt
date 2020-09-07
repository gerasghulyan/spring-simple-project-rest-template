package com.vntana.core.service.comment.product.impl

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 04.09.2020
 * Time: 13:12
 */
class ProductCommentExistByUuidServiceUnitTest : AbstractProductCommentServiceUnitTest() {
    
    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { productCommentService.existsByUuid(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { productCommentService.existsByUuid(emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when does not exist`() {
        val uuid = uuid()
        resetAll()
        EasyMock.expect(productCommentRepository.existsByUuid(uuid)).andReturn(false)
        replayAll()
        assertThat(productCommentService.existsByUuid(uuid)).isFalse()
        verifyAll()
    }

    @Test
    fun `test when found`() {
        val uuid = uuid()
        resetAll()
        EasyMock.expect(productCommentRepository.existsByUuid(uuid)).andReturn(true)
        replayAll()
        assertThat(productCommentService.existsByUuid(uuid)).isTrue()
        verifyAll()
    }
}