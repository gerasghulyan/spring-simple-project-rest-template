package com.vntana.core.rest.facade.comment.impl

import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 4:44 PM
 */
class ProductCommentFindByFilterFacadeImplUnitTest : AbstractProductCommentFacadeImplUnitTest() {

    @Test
    fun test() {
        val request = restTestHelper.buildFindProductCommentByFilterRequestModel()
        val dto = commonTestHelper.buildProductCommentFindByProductUuidDto(
                page = request.page,
                size = request.size,
                productUuid = request.productUuid
        )
        val comment1 = commonTestHelper.buildProductComment()
        val comment2 = commonTestHelper.buildProductComment()
        val totalSize = 100L
        val page = commonTestHelper.buildProductCommentPage(
                productUuid = request.productUuid,
                entities = listOf(comment1, comment2),
                total = totalSize
        )
        val comment1ViewModel = restTestHelper.buildProductCommentViewResponseModel()
        val comment2ViewModel = restTestHelper.buildProductCommentViewResponseModel()
        resetAll()
        expect(productCommentService.findByProductUuid(dto)).andReturn(page)
        expect(productCommentViewModelBuilder.build(comment1)).andReturn(comment1ViewModel)
        expect(productCommentViewModelBuilder.build(comment2)).andReturn(comment2ViewModel)
        replayAll()
        productCommentFacade.findByFilter(request)
                .apply { assertBasicSuccessResultResponse(this) }
                .response()
                .apply { assertThat(this.totalCount()).isEqualTo(totalSize) }
                .items()
                .apply { assertThat(this.size).isEqualTo(2) }
                .apply { assertThat(this[0]).isEqualTo(comment1ViewModel) }
                .apply { assertThat(this[1]).isEqualTo(comment2ViewModel) }
        verifyAll()
    }
}