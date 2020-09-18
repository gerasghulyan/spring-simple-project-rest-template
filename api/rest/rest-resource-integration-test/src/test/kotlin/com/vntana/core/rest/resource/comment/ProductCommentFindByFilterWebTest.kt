package com.vntana.core.rest.resource.comment

import com.vntana.core.model.comment.CommentErrorResponseModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.http.HttpStatus

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 5:22 PM
 */
class ProductCommentFindByFilterWebTest : AbstractProductCommentWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                productCommentResourceClient.search(resourceTestHelper.buildFindProductCommentByFilterRequestModel(page = null)),
                CommentErrorResponseModel.MISSING_PAGE
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                productCommentResourceClient.search(resourceTestHelper.buildFindProductCommentByFilterRequestModel(size = null)),
                CommentErrorResponseModel.MISSING_SIZE
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                productCommentResourceClient.search(resourceTestHelper.buildFindProductCommentByFilterRequestModel(productUuid = null)),
                CommentErrorResponseModel.MISSING_PRODUCT_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                productCommentResourceClient.search(resourceTestHelper.buildFindProductCommentByFilterRequestModel(productUuid = "")),
                CommentErrorResponseModel.MISSING_PRODUCT_UUID
        )
    }

    @Test
    fun `test when nothing found`() {
        resourceTestHelper.buildFindProductCommentByFilterRequestModel()
                .let { productCommentResourceClient.search(it) }
                .apply { assertBasicSuccessResultResponse(this) }
                .body!!.response()
                .apply { assertThat(this.totalCount()).isEqualTo(0) }
                .apply { assertThat(this.items()).isEmpty() }
    }

    @Test
    fun test() {
        val userFullName = uuid()
        val userUuid = userResourceTestHelper.persistUser(
                userResourceTestHelper.buildCreateUserRequest(fullName = userFullName)
        ).response().uuid
        val productUuid = uuid()
        val message = resourceTestHelper.buildCommentWithTaggedUser(userUuid = userUuid)
        resourceTestHelper.persistProductComment()
        resourceTestHelper.persistProductComment(
                userUuid = userUuid,
                productUuid = productUuid,
                message = message
        )
        resourceTestHelper.buildFindProductCommentByFilterRequestModel(productUuid = productUuid)
                .let { productCommentResourceClient.search(it) }
                .apply { assertBasicSuccessResultResponse(this) }
                .body!!.response()
                .apply { assertThat(this.totalCount()).isEqualTo(1) }
                .items()
                .apply { assertThat(this.size).isEqualTo(1) }[0]
                .apply { assertThat(this.productUuid).isEqualTo(productUuid) }
                .apply { assertThat(this.message).isEqualTo(message) }
                .apply {
                    assertThat(this.taggedUsers).isNotEmpty
                    assertThat(this.taggedUsers[0].uuid).isEqualTo(userUuid)
                    assertThat(this.taggedUsers[0].fullName).isEqualTo(userFullName)
                }
                .owner
                .apply { assertThat(this.fullName).isEqualTo(userFullName) }
                .apply { assertThat(this.uuid).isEqualTo(userUuid) }
    }
}