package com.vntana.core.rest.resource.comment

import com.vntana.core.model.comment.CommentErrorResponseModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.http.HttpStatus

/**
 * Created by Vardan Aivazian
 * Date: 10.09.2020
 * Time: 17:57
 **/
class ProductCommentUpdateWebTest : AbstractProductCommentWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                productCommentResourceClient.update(resourceTestHelper.buildUpdateProductCommentRequestModel(uuid = null)),
                CommentErrorResponseModel.MISSING_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                productCommentResourceClient.update(resourceTestHelper.buildUpdateProductCommentRequestModel(uuid = emptyString())),
                CommentErrorResponseModel.MISSING_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                productCommentResourceClient.update(resourceTestHelper.buildUpdateProductCommentRequestModel(uuid = " ")),
                CommentErrorResponseModel.MISSING_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                productCommentResourceClient.update(resourceTestHelper.buildUpdateProductCommentRequestModel(userUuid = null)),
                CommentErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                productCommentResourceClient.update(resourceTestHelper.buildUpdateProductCommentRequestModel(userUuid = emptyString())),
                CommentErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                productCommentResourceClient.update(resourceTestHelper.buildUpdateProductCommentRequestModel(userUuid = " ")),
                CommentErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                productCommentResourceClient.update(resourceTestHelper.buildUpdateProductCommentRequestModel(message = null)),
                CommentErrorResponseModel.MISSING_MESSAGE
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                productCommentResourceClient.update(resourceTestHelper.buildUpdateProductCommentRequestModel(message = emptyString())),
                CommentErrorResponseModel.MISSING_MESSAGE
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                productCommentResourceClient.update(resourceTestHelper.buildUpdateProductCommentRequestModel(message = " ")),
                CommentErrorResponseModel.MISSING_MESSAGE
        )
    }

    @Test
    fun `test when comment not found`() {
        resourceTestHelper.buildUpdateProductCommentRequestModel()
                .let { productCommentResourceClient.update(it) }
                .apply {
                    assertBasicErrorResultResponse(
                            HttpStatus.NOT_FOUND,
                            this,
                            CommentErrorResponseModel.COMMENT_NOT_FOUND
                    )
                }
    }

    @Test
    fun `test user access denied`() {
        val userUuid = userResourceTestHelper.persistUser().response().uuid
        val uuid = resourceTestHelper.persistProductComment()
        resourceTestHelper.buildUpdateProductCommentRequestModel(uuid = uuid, userUuid = userUuid)
                .let { productCommentResourceClient.update(it) }
                .apply {
                    assertBasicErrorResultResponse(
                            HttpStatus.FORBIDDEN,
                            this,
                            CommentErrorResponseModel.USER_ACCESS_DENIED
                    )
                }
    }

    @Test
    fun test() {
        val message = uuid()
        val userUuid = userResourceTestHelper.persistUser().response().uuid
        val uuid = resourceTestHelper.persistProductComment(userUuid = userUuid)
        resourceTestHelper.buildUpdateProductCommentRequestModel(uuid = uuid, userUuid = userUuid, message = message)
                .let { productCommentResourceClient.update(it) }
                .apply { assertBasicSuccessResultResponse(this) }
                .body!!.response()
                .apply {
                    assertThat(this.uuid).isEqualTo(uuid)
                    assertThat(this.message).isEqualTo(message)
                }
    }
}