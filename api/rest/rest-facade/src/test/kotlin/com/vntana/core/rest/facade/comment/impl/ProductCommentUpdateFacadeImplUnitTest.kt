package com.vntana.core.rest.facade.comment.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.comment.CommentErrorResponseModel
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 11.09.2020
 * Time: 1:43
 **/
class ProductCommentUpdateFacadeImplUnitTest : AbstractProductCommentFacadeImplUnitTest() {

    @Test
    fun `test when precondition failed`() {
        val request = restTestHelper.buildUpdateProductCommentRequestModel()
        val error = SingleErrorWithStatus.of(404, CommentErrorResponseModel.COMMENT_NOT_FOUND)
        resetAll()
        expect(preconditionChecker.checkUpdateProductComment(request)).andReturn(error)
        replayAll()
        productCommentFacade.update(request)
                .apply { assertBasicErrorResultResponse(this, error.error) }
                .apply { assertThat(this.httpStatusCode).isEqualTo(error.httpStatus) }
        verifyAll()
    }

    @Test
    fun test() {
        val request = restTestHelper.buildUpdateProductCommentRequestModel()
        val dto = commonTestHelper.buildProductCommentUpdateDto(
                uuid = request.uuid,
                message = request.message
        )
        val comment = commonTestHelper.buildProductComment()
        resetAll()
        expect(preconditionChecker.checkUpdateProductComment(request)).andReturn(SingleErrorWithStatus.empty())
        expect(productCommentService.update(dto)).andReturn(comment)
        replayAll()
        productCommentFacade.update(request)
                .apply { assertBasicSuccessResultResponse(this) }
                .response()
                .apply { assertThat(this.uuid).isEqualTo(comment.uuid) }
                .apply { assertThat(this.message).isEqualTo(comment.message) }
        verifyAll()
    }
}