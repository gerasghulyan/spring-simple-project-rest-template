package com.vntana.core.service.comment.impl

import com.vntana.commons.service.exception.EntityNotFoundForUuidException
import org.assertj.core.api.Assertions
import org.easymock.EasyMock
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
        Assertions.assertThatThrownBy { commentService.findByUuid(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy { commentService.findByUuid(emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when comment not found`() {
        val uuid = uuid()
        resetAll()
        EasyMock.expect(commentRepository.findByUuid(uuid)).andReturn(Optional.empty())
        replayAll()

        Assertions.assertThatThrownBy { commentService.findByUuid(uuid) }
                .isExactlyInstanceOf(EntityNotFoundForUuidException::class.java)
        verifyAll()
    }

    @Test
    fun `test when found`() {
        val comment = commonTestHelper.buildComment()
        resetAll()
        EasyMock.expect(commentRepository.findByUuid(comment.uuid)).andReturn(Optional.of(comment))
        replayAll()
        commentService.findByUuid(comment.uuid).let {
            Assertions.assertThat(it.uuid).isEqualTo(comment.uuid)
        }
        verifyAll()
    }
}