package com.vntana.core.service.comment.impl

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
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
        assertThatThrownBy { commentService.existsByUuid(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commentService.existsByUuid(emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when does not exist`() {
        val uuid = uuid()
        resetAll()
        expect(commentService.existsByUuid(uuid)).andReturn(false)
        replayAll()
        assertThat(commentService.existsByUuid(uuid)).isFalse()
        verifyAll()
    }

    @Test
    fun `test when found`() {
        val uuid = uuid()
        resetAll()
        expect(commentService.existsByUuid(uuid)).andReturn(true)
        replayAll()
        assertThat(commentService.existsByUuid(uuid)).isTrue()
        verifyAll()
    }
}