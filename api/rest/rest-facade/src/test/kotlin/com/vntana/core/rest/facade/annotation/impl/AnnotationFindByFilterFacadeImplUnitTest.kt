package com.vntana.core.rest.facade.annotation.impl

import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 16.09.2020
 * Time: 17:21
 */
class AnnotationFindByFilterFacadeImplUnitTest : AbstractAnnotationFacadeImplUnitTest() {

    @Test
    fun test() {
        val request = restTestHelper.buildFindAnnotationByFilterRequestModel()
        val dto = commonTestHelper.buildAnnotationFindByProductUuidDto(
                page = request.page,
                size = request.size,
                productUuid = request.productUuid
        )
        val annotation1 = commonTestHelper.buildAnnotation()
        val annotation2 = commonTestHelper.buildAnnotation()
        val totalSize = 100L
        val page = commonTestHelper.buildAnnotationPage(
                productUuid = request.productUuid,
                entities = listOf(annotation1, annotation2),
                total = totalSize
        )
        val annotation1ViewModel = restTestHelper.buildAnnotationViewResponseModel()
        val annotation2ViewModel = restTestHelper.buildAnnotationViewResponseModel()
        resetAll()
        expect(annotationService.findByProductUuid(dto)).andReturn(page)
        expect(annotationViewModelBuilder.build(annotation1)).andReturn(annotation1ViewModel)
        expect(annotationViewModelBuilder.build(annotation2)).andReturn(annotation2ViewModel)
        replayAll()
        annotationFacade.findByFilter(request)
                .apply { assertBasicSuccessResultResponse(this) }
                .response()
                .apply { assertThat(this.totalCount()).isEqualTo(totalSize) }
                .items().let {
                    assertThat(it.size).isEqualTo(2)
                    assertThat(it[0]).isEqualTo(annotation1ViewModel)
                    assertThat(it[1]).isEqualTo(annotation2ViewModel)
                }
        verifyAll()
    }
}