package com.vntana.core.rest.facade.comment.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.comment.CommentErrorResponseModel
import org.apache.http.HttpStatus.SC_NOT_FOUND
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 4:28 PM
 */
class ProductCommentCheckCreateFacadePreconditionCheckerImplUnitTest : AbstractProductCommentFacadePreconditionCheckerImplUnitTest() {

    @Test
    fun `test when user does not exist`() {
        val request = restTestHelper.buildCreateProductCommentRequestModel()
        resetAll()
        expect(userService.existsByUuid(request.userUuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkCreateProductComment(request)
                .apply { assertThat(this.isPresent).isTrue() }
                .apply { assertThat(this.error).isEqualTo(CommentErrorResponseModel.USER_NOT_FOUND) }
                .apply { assertThat(this.httpStatus).isEqualTo(SC_NOT_FOUND) }
        verifyAll()
    }

    @Test
    fun test() {
        val request = restTestHelper.buildCreateProductCommentRequestModel()
        resetAll()
        expect(userService.existsByUuid(request.userUuid)).andReturn(true)
        replayAll()
        preconditionChecker.checkCreateProductComment(request)
                .apply { assertThat(this).isEqualTo(SingleErrorWithStatus.empty<CommentErrorResponseModel>()) }
        verifyAll()
    }
}