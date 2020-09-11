package com.vntana.core.rest.facade.comment.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.comment.CommentErrorResponseModel
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 10.09.2020
 * Time: 16:35
 **/
class ProductCommentCheckDeleteFacadePreconditionCheckerImplUnitTest : AbstractProductCommentFacadePreconditionCheckerImplUnitTest() {

    @Test
    fun `test when comment not found`() {
        val request = restTestHelper.buildDeleteProductCommentRequestModel()
        resetAll()
        expect(commentService.existsByUuid(request.uuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkDeleteProductComment(request).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.error).isEqualTo(CommentErrorResponseModel.COMMENT_NOT_FOUND)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
        }
        verifyAll()
    }
    
    @Test
    fun `test when user access denied`() {
        val request = restTestHelper.buildDeleteProductCommentRequestModel()
        val comment = commonTestHelper.buildProductComment()
        resetAll()
        expect(commentService.existsByUuid(request.uuid)).andReturn(true)
        expect(commentService.findByUuid(request.uuid)).andReturn(comment)
        replayAll()
        preconditionChecker.checkDeleteProductComment(request).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.error).isEqualTo(CommentErrorResponseModel.USER_ACCESS_DENIED)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_FORBIDDEN)
        }
        verifyAll()
    }

    @Test
    fun test() {
        val comment = commonTestHelper.buildProductComment()
        val request = restTestHelper.buildDeleteProductCommentRequestModel(userUuid = comment.user.uuid)
        resetAll()
        expect(commentService.existsByUuid(request.uuid)).andReturn(true)
        expect(commentService.findByUuid(request.uuid)).andReturn(comment)
        replayAll()
        preconditionChecker.checkDeleteProductComment(request).let {
            assertThat(it).isEqualTo(SingleErrorWithStatus.empty<CommentErrorResponseModel>())
        }
        verifyAll()
    }
}