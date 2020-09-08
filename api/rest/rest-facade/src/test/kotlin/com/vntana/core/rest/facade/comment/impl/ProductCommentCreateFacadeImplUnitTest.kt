package com.vntana.core.rest.facade.comment.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.comment.CommentErrorResponseModel
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 4:36 PM
 */
class ProductCommentCreateFacadeImplUnitTest : AbstractProductCommentFacadeImplUnitTest() {

    @Test
    fun `test when precondition failed`() {
        val request = restTestHelper.buildCreateProductCommentRequestModel()
        val error = SingleErrorWithStatus.of(404, CommentErrorResponseModel.USER_NOT_FOUND)
        resetAll()
        expect(preconditionChecker.checkCreateProductComment(request)).andReturn(error)
        replayAll()
        productCommentFacade.create(request)
                .apply { assertBasicErrorResultResponse(this, error.error) }
                .apply { assertThat(this.httpStatusCode).isEqualTo(error.httpStatus) }
        verifyAll()
    }

    @Test
    fun test() {
        val request = restTestHelper.buildCreateProductCommentRequestModel()
        val dto = commonTestHelper.buildProductCommentCreateDto(
                userUuid = request.userUuid,
                productUuid = request.productUuid,
                message = request.message
        )
        val comment = commonTestHelper.buildProductComment()
        resetAll()
        expect(preconditionChecker.checkCreateProductComment(request)).andReturn(SingleErrorWithStatus.empty())
        expect(productCommentService.create(dto)).andReturn(comment)
        replayAll()
        productCommentFacade.create(request)
                .apply { assertBasicSuccessResultResponse(this) }
                .response()
                .apply { assertThat(this.uuid).isEqualTo(comment.uuid) }
        verifyAll()
    }
}