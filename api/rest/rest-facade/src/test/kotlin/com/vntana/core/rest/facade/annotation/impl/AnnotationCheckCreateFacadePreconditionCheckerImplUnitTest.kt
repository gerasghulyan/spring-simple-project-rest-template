package com.vntana.core.rest.facade.annotation.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.annotation.AnnotationErrorResponseModel
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 16.09.2020
 * Time: 14:40
 */
class AnnotationCheckCreateFacadePreconditionCheckerImplUnitTest : AbstractAnnotationFacadePreconditionCheckerImplUnitTest() {

    @Test
    fun `test when user does not exist`() {
        val request = restTestHelper.buildCreateAnnotationRequestModel()
        resetAll()
        expect(userService.existsByUuid(request.userUuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkCreateAnnotation(request).let { 
            assertThat(it.isPresent).isTrue()
            assertThat(it.error).isEqualTo(AnnotationErrorResponseModel.USER_NOT_FOUND)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun test() {
        val request = restTestHelper.buildCreateAnnotationRequestModel()
        resetAll()
        expect(userService.existsByUuid(request.userUuid)).andReturn(true)
        replayAll()
        preconditionChecker.checkCreateAnnotation(request).let { 
            assertThat(it).isEqualTo(SingleErrorWithStatus.empty<AnnotationErrorResponseModel>())
        }
        verifyAll()
    }
    
    
}