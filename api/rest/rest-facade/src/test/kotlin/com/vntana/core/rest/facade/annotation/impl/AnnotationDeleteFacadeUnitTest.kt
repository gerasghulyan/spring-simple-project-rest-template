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
 * Time: 17:16
 */
class AnnotationDeleteFacadeUnitTest : AbstractAnnotationFacadeImplUnitTest() {

    @Test
    fun `test when precondition failed`() {
        val request = restTestHelper.buildDeleteAnnotationRequestModel()
        val error = SingleErrorWithStatus.of(SC_NOT_FOUND, AnnotationErrorResponseModel.ANNOTATION_NOT_FOUND)
        resetAll()
        expect(preconditionChecker.checkDeleteAnnotation(request)).andReturn(error)
        replayAll()
        annotationFacade.delete(request).let {
            assertBasicErrorResultResponse(it, error.error)
            assertThat(it.httpStatusCode).isEqualTo(error.httpStatus)
        }
        verifyAll()
    }

    @Test
    fun test() {
        val request = restTestHelper.buildDeleteAnnotationRequestModel()
        val annotation = commonTestHelper.buildAnnotation()
        resetAll()
        expect(preconditionChecker.checkDeleteAnnotation(request)).andReturn(SingleErrorWithStatus.empty())
        expect(annotationService.delete(request.uuid)).andReturn(annotation)
        replayAll()
        annotationFacade.delete(request)
                .apply { assertBasicSuccessResultResponse(this) }
                .response()
                .apply { assertThat(this.uuid).isEqualTo(annotation.uuid) }
        verifyAll()
    }
    
}