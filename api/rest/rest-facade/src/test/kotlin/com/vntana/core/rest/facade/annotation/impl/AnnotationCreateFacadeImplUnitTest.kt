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
 * Time: 16:21
 */
class AnnotationCreateFacadeImplUnitTest : AbstractAnnotationFacadeImplUnitTest() {

    @Test
    fun `test when precondition failed`() {
        val request = restTestHelper.buildCreateAnnotationRequestModel()
        val error = SingleErrorWithStatus.of(SC_NOT_FOUND, AnnotationErrorResponseModel.USER_NOT_FOUND)
        resetAll()
        expect(preconditionChecker.checkCreateAnnotation(request)).andReturn(error)
        replayAll()
        annotationFacade.create(request).let {
            assertBasicErrorResultResponse(it, error.error)
            assertThat(it.httpStatusCode).isEqualTo(error.httpStatus)
        }
        verifyAll()
    }

    @Test
    fun test() {
        val request = restTestHelper.buildCreateAnnotationRequestModel()
        val dto = commonTestHelper.buildAnnotationCreateDto(
                userUuid = request.userUuid,
                productUuid = request.productUuid,
                text = request.text,
                number = request.number,
                d1 = request.d1,
                d2 = request.d2,
                d3 = request.d3
        )
        val annotation = commonTestHelper.buildAnnotation()
        resetAll()
        expect(preconditionChecker.checkCreateAnnotation(request)).andReturn(SingleErrorWithStatus.empty())
        expect(annotationService.create(dto)).andReturn(annotation)
        replayAll()
        annotationFacade.create(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().uuid).isEqualTo(annotation.uuid)
        }
        verifyAll()
    }
}