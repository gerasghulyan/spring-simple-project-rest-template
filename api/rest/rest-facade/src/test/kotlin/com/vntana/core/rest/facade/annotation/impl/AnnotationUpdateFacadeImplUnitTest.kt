package com.vntana.core.rest.facade.annotation.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.annotation.AnnotationErrorResponseModel
import org.apache.http.HttpStatus.SC_NOT_FOUND
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 16.09.2020
 * Time: 16:41
 */
class AnnotationUpdateFacadeImplUnitTest : AbstractAnnotationFacadeImplUnitTest() {

    @Test
    fun `test when precondition failed`() {
        val request = restTestHelper.buildUpdateAnnotationRequestModel()
        val error = SingleErrorWithStatus.of(SC_NOT_FOUND, AnnotationErrorResponseModel.ANNOTATION_NOT_FOUND)
        resetAll()
        expect(preconditionChecker.checkUpdateAnnotation(request)).andReturn(error)
        replayAll()
        annotationFacade.update(request).let {
            assertBasicErrorResultResponse(it, error.error)
            assertThat(it.httpStatusCode).isEqualTo(error.httpStatus)
        }
        verifyAll()
    }

    @Test
    fun test() {
        val request = restTestHelper.buildUpdateAnnotationRequestModel()
        val dto = commonTestHelper.buildAnnotationUpdateDto(
                uuid = request.uuid,
                text = request.text,
                d1 = request.d1,
                d2 = request.d2,
                d3 = request.d3
        )
        val annotation = commonTestHelper.buildAnnotation()
        resetAll()
        expect(preconditionChecker.checkUpdateAnnotation(request)).andReturn(SingleErrorWithStatus.empty())
        expect(annotationService.update(dto)).andReturn(annotation)
        replayAll()
        annotationFacade.update(request)
                .apply { assertBasicSuccessResultResponse(this) }
                .response().let { 
                    assertThat(it.uuid).isEqualTo(annotation.uuid)
                    assertThat(it.text).isEqualTo(annotation.text)
                    assertThat(it.d1).isEqualTo(annotation.d1)
                    assertThat(it.d2).isEqualTo(annotation.d2)
                    assertThat(it.d3).isEqualTo(annotation.d3)
                }
        verifyAll()
    }
}