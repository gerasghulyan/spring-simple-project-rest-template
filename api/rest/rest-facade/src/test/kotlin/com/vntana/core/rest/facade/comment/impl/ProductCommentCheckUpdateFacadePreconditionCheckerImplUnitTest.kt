package com.vntana.core.rest.facade.comment.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.comment.CommentErrorResponseModel
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Vardan Aivazian
 * Date: 10.09.2020
 * Time: 16:22
 **/
class ProductCommentCheckUpdateFacadePreconditionCheckerImplUnitTest : AbstractProductCommentFacadePreconditionCheckerImplUnitTest() {

    @Test
    fun `test when comment does not exist`() {
        val request = restTestHelper.buildUpdateProductCommentRequestModel()
        resetAll()
        expect(commentService.findByUuid(request.uuid)).andReturn(Optional.empty())
        replayAll()
        preconditionChecker.checkUpdateProductComment(request).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.error).isEqualTo(CommentErrorResponseModel.COMMENT_NOT_FOUND)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun `test when user access denied`() {
        val request = restTestHelper.buildUpdateProductCommentRequestModel()
        val comment = commonTestHelper.buildProductComment()
        resetAll()
        expect(commentService.findByUuid(request.uuid)).andReturn(Optional.of(comment))
        replayAll()
        preconditionChecker.checkUpdateProductComment(request).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.error).isEqualTo(CommentErrorResponseModel.USER_ACCESS_DENIED)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_FORBIDDEN)
        }
        verifyAll()
    }

    @Test
    fun test() {
        val comment = commonTestHelper.buildProductComment()
        val request = restTestHelper.buildUpdateProductCommentRequestModel(userUuid = comment.user.uuid)
        resetAll()
        expect(commentService.findByUuid(request.uuid)).andReturn(Optional.of(comment))
        replayAll()
        preconditionChecker.checkUpdateProductComment(request).let {
            assertThat(it).isEqualTo(SingleErrorWithStatus.empty<CommentErrorResponseModel>())
        }
        verifyAll()
    }
}