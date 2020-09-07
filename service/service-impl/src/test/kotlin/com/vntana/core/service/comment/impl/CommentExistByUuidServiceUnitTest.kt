package com.vntana.core.service.comment.impl

import org.assertj.core.api.Assertions
import org.easymock.EasyMock
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 07.09.2020
 * Time: 16:40
 **/
class CommentExistByUuidServiceUnitTest : AbstractCommentServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        Assertions.assertThatThrownBy { commentService.existsByUuid(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy { commentService.existsByUuid(emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when does not exist`() {
        val uuid = uuid()
        resetAll()
        EasyMock.expect(commentService.existsByUuid(uuid)).andReturn(false)
        replayAll()
        Assertions.assertThat(commentService.existsByUuid(uuid)).isFalse()
        verifyAll()
    }

    @Test
    fun `test when found`() {
        val uuid = uuid()
        resetAll()
        EasyMock.expect(commentService.existsByUuid(uuid)).andReturn(true)
        replayAll()
        Assertions.assertThat(commentService.existsByUuid(uuid)).isTrue()
        verifyAll()
    }
}