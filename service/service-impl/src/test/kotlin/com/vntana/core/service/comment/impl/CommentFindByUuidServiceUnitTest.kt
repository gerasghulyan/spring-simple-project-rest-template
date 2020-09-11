package com.vntana.core.service.comment.impl

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Vardan Aivazian
 * Date: 07.09.2020
 * Time: 16:40
 **/
class CommentFindByUuidServiceUnitTest : AbstractCommentServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { commentService.findByUuid(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commentService.findByUuid(emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when comment not found`() {
        val uuid = uuid()
        resetAll()
        expect(commentRepository.findByUuid(uuid)).andReturn(Optional.empty())
        replayAll()
        commentService.findByUuid(uuid).let {
            assertThat(it.isPresent).isFalse()
        }
        verifyAll()
    }

    @Test
    fun `test when found`() {
        val comment = commonTestHelper.buildProductComment()
        resetAll()
        expect(commentRepository.findByUuid(comment.uuid)).andReturn(Optional.of(comment))
        replayAll()
        commentService.findByUuid(comment.uuid).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.get()).isEqualTo(comment)
        }
        verifyAll()
    }
}