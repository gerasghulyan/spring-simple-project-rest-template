package com.vntana.core.rest.resource.comment

import com.vntana.core.model.comment.CommentErrorResponseModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.http.HttpStatus

/**
 * Created by Vardan Aivazian
 * Date: 11.09.2020
 * Time: 1:18
 **/
class ProductCommentDeleteWebTest : AbstractProductCommentWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                productCommentResourceClient.delete(resourceTestHelper.buildDeleteProductCommentRequestModel(uuid = null)),
                CommentErrorResponseModel.MISSING_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                productCommentResourceClient.delete(resourceTestHelper.buildDeleteProductCommentRequestModel(uuid = emptyString())),
                CommentErrorResponseModel.MISSING_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                productCommentResourceClient.delete(resourceTestHelper.buildDeleteProductCommentRequestModel(uuid = " ")),
                CommentErrorResponseModel.MISSING_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                productCommentResourceClient.delete(resourceTestHelper.buildDeleteProductCommentRequestModel(userUuid = null)),
                CommentErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                productCommentResourceClient.delete(resourceTestHelper.buildDeleteProductCommentRequestModel(userUuid = emptyString())),
                CommentErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                productCommentResourceClient.delete(resourceTestHelper.buildDeleteProductCommentRequestModel(userUuid = " ")),
                CommentErrorResponseModel.MISSING_USER_UUID
        )
    }

    @Test
    fun `test when comment not found`() {
        resourceTestHelper.buildDeleteProductCommentRequestModel()
                .let { productCommentResourceClient.delete(it) }
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
        resourceTestHelper.buildDeleteProductCommentRequestModel(uuid = uuid, userUuid = userUuid)
                .let { productCommentResourceClient.delete(it) }
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
        val productUuid = uuid()
        val userUuid = userResourceTestHelper.persistUser().response().uuid
        val uuid = resourceTestHelper.persistProductComment(userUuid = userUuid, productUuid = productUuid)
        resourceTestHelper.buildDeleteProductCommentRequestModel(uuid = uuid, userUuid = userUuid)
                .let { productCommentResourceClient.delete(it) }
                .apply { assertBasicSuccessResultResponse(this) }
                .body!!.response().uuid
                .apply {assertThat(this).isEqualTo(uuid)}
                .apply {
                    val existingCommentsUuids = resourceTestHelper.buildFindProductCommentByFilterRequestModel(productUuid = productUuid)
                            .let { productCommentResourceClient.search(it) }
                            .body!!.response()
                            .items().map { it.uuid }
                    assertThat(existingCommentsUuids).doesNotContain(uuid)
                }
    }
}