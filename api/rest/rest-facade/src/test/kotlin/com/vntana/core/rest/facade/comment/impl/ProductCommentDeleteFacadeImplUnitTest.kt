package com.vntana.core.rest.facade.comment.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.comment.CommentErrorResponseModel
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 11.09.2020
 * Time: 1:49
 **/
class ProductCommentDeleteFacadeImplUnitTest : AbstractProductCommentFacadeImplUnitTest() {

    @Test
    fun `test when precondition failed`() {
        val request = restTestHelper.buildDeleteProductCommentRequestModel()
        val error = SingleErrorWithStatus.of(404, CommentErrorResponseModel.COMMENT_NOT_FOUND)
        resetAll()
        expect(preconditionChecker.checkDeleteProductComment(request)).andReturn(error)
        replayAll()
        productCommentFacade.delete(request)
                .apply { assertBasicErrorResultResponse(this, error.error) }
                .apply { assertThat(this.httpStatusCode).isEqualTo(error.httpStatus) }
        verifyAll()
    }

    @Test
    fun test() {
        val request = restTestHelper.buildDeleteProductCommentRequestModel()
        val comment = commonTestHelper.buildProductComment()
        resetAll()
        expect(preconditionChecker.checkDeleteProductComment(request)).andReturn(SingleErrorWithStatus.empty())
        expect(commentService.delete(request.uuid)).andReturn(comment)
        replayAll()
        productCommentFacade.delete(request)
                .apply { assertBasicSuccessResultResponse(this) }
                .response()
                .apply { assertThat(this.uuid).isEqualTo(comment.uuid) }
        verifyAll()
    }
}