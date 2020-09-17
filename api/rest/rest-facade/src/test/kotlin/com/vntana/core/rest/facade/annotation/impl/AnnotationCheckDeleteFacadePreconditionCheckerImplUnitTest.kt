package com.vntana.core.rest.facade.annotation.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.annotation.AnnotationErrorResponseModel
import org.apache.http.HttpStatus.SC_FORBIDDEN
import org.apache.http.HttpStatus.SC_NOT_FOUND
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Vardan Aivazian
 * Date: 16.09.2020
 * Time: 16:06
 */
class AnnotationCheckDeleteFacadePreconditionCheckerImplUnitTest : AbstractAnnotationFacadePreconditionCheckerImplUnitTest() {

    @Test
    fun `test when annotation not found`() {
        val request = restTestHelper.buildDeleteAnnotationRequestModel()
        resetAll()
        expect(annotationService.findByUuid(request.uuid)).andReturn(Optional.empty())
        replayAll()
        preconditionChecker.checkDeleteAnnotation(request).let { 
            assertThat(it.isPresent).isTrue()
            assertThat(it.error).isEqualTo(AnnotationErrorResponseModel.ANNOTATION_NOT_FOUND)
            assertThat(it.httpStatus).isEqualTo(SC_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun `test when user access denied`() {
        val request = restTestHelper.buildDeleteAnnotationRequestModel()
        val annotation = commonTestHelper.buildAnnotation()
        resetAll()
        expect(annotationService.findByUuid(request.uuid)).andReturn(Optional.of(annotation))
        replayAll()
        preconditionChecker.checkDeleteAnnotation(request).let { 
            assertThat(it.isPresent).isTrue()
            assertThat(it.error).isEqualTo(AnnotationErrorResponseModel.USER_ACCESS_DENIED)
            assertThat(it.httpStatus).isEqualTo(SC_FORBIDDEN)
        }
        verifyAll()
    }

    @Test
    fun test() {
        val annotation = commonTestHelper.buildAnnotation()
        val request = restTestHelper.buildDeleteAnnotationRequestModel(userUuid = annotation.user.uuid)
        resetAll()
        expect(annotationService.findByUuid(request.uuid)).andReturn(Optional.of(annotation))
        replayAll()
        preconditionChecker.checkDeleteAnnotation(request).let { 
            assertThat(it).isEqualTo(SingleErrorWithStatus.empty<AnnotationErrorResponseModel>())
        }
        verifyAll()
    }
}