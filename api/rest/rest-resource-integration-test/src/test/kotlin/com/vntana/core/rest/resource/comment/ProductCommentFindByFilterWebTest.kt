package com.vntana.core.rest.resource.comment

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 5:22 PM
 */
class ProductCommentFindByFilterWebTest : AbstractProductCommentWebTest() {

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
        val message = uuid()
        resourceTestHelper.persistProductComment()
        resourceTestHelper.persistProductComment(userUuid = userUuid, productUuid = productUuid, message = message)
        resourceTestHelper.buildFindProductCommentByFilterRequestModel(productUuid = productUuid)
                .let { productCommentResourceClient.search(it) }
                .apply { assertBasicSuccessResultResponse(this) }
                .body!!.response()
                .apply { assertThat(this.totalCount()).isEqualTo(1) }
                .items()
                .apply { assertThat(this.size).isEqualTo(1) }[0]
                .apply { assertThat(this.productUuid).isEqualTo(productUuid) }
                .apply { assertThat(this.message).isEqualTo(message) }
                .owner
                .apply { assertThat(this.fullName).isEqualTo(userFullName) }
                .apply { assertThat(this.uuid).isEqualTo(userUuid) }
    }
}