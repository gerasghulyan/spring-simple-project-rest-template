package com.vntana.core.rest.resource.comment

import com.vntana.core.model.comment.CommentErrorResponseModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.http.HttpStatus

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 5:09 PM
 */
class ProductCommentCreateWebTest : AbstractProductCommentWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                productCommentResourceClient.create(resourceTestHelper.buildCreateProductCommentRequestModel(userUuid = null)),
                CommentErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                productCommentResourceClient.create(resourceTestHelper.buildCreateProductCommentRequestModel(userUuid = emptyString())),
                CommentErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                productCommentResourceClient.create(resourceTestHelper.buildCreateProductCommentRequestModel(productUuid = null)),
                CommentErrorResponseModel.MISSING_PRODUCT_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                productCommentResourceClient.create(resourceTestHelper.buildCreateProductCommentRequestModel(productUuid = emptyString())),
                CommentErrorResponseModel.MISSING_PRODUCT_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                productCommentResourceClient.create(resourceTestHelper.buildCreateProductCommentRequestModel(message = null)),
                CommentErrorResponseModel.MISSING_MESSAGE
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                productCommentResourceClient.create(resourceTestHelper.buildCreateProductCommentRequestModel(message = emptyString())),
                CommentErrorResponseModel.MISSING_MESSAGE
        )
    }

    @Test
    fun `test when user not found`() {
        resourceTestHelper.buildCreateProductCommentRequestModel()
                .let { productCommentResourceClient.create(it) }
                .apply {
                    assertBasicErrorResultResponse(
                            HttpStatus.NOT_FOUND,
                            this,
                            CommentErrorResponseModel.USER_NOT_FOUND
                    )
                }

    }

    @Test
    fun test() {
        userResourceTestHelper.persistUser().response().uuid
                .let { resourceTestHelper.buildCreateProductCommentRequestModel(userUuid = it) }
                .let { productCommentResourceClient.create(it) }
                .apply { assertBasicSuccessResultResponse(this) }
                .body!!.response()
                .apply { assertThat(this.uuid).isNotNull() }
    }
}