package com.vntana.core.service.comment.impl

import com.vntana.core.domain.comment.AbstractComment
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.*
import org.junit.Test
import java.util.*

/**
 * Created by Vardan Aivazian
 * Date: 04.09.2020
 * Time: 13:48
 */
class CommentDeleteServiceUnitTest : AbstractCommentServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { commentService.delete(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commentService.delete(emptyString()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test delete when found`() {
        val comment = commonTestHelper.buildProductComment()
        resetAll()
        expect(commentRepository.findByUuid(comment.uuid)).andReturn(Optional.of(comment))
        expect(commentRepository.save(isA(AbstractComment::class.java))).andAnswer { getCurrentArguments()[0] as AbstractComment }
        replayAll()
        commentService.delete(comment.uuid).let {
            assertThat(it.removed).isNotNull()
        }
        verifyAll()
    }
}