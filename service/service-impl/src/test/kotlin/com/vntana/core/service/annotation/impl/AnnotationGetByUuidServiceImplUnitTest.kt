package com.vntana.core.service.annotation.impl

import com.vntana.commons.service.exception.EntityNotFoundForUuidException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Vardan Aivazian
 * Date: 15.09.2020
 * Time: 13:58
 */
class AnnotationGetByUuidServiceImplUnitTest : AbstractAnnotationServiceImplUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertIllegalArgumentException { annotationService.getByUuid(null) }
        assertIllegalArgumentException { annotationService.getByUuid(emptyString()) }
        verifyAll()
    }

    @Test
    fun `test when not found`() {
        val uuid = uuid()
        resetAll()
        expect(annotationRepository.findByUuid(uuid)).andReturn(Optional.empty())
        replayAll()
        assertThatThrownBy { annotationService.getByUuid(uuid) }
                .isExactlyInstanceOf(EntityNotFoundForUuidException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        val annotation = commonTestHelper.buildAnnotation()
        resetAll()
        expect(annotationRepository.findByUuid(annotation.uuid)).andReturn(Optional.of(annotation))
        replayAll()
        annotationService.getByUuid(annotation.uuid).let {
            assertThat(it).isEqualTo(annotation)
        }
        verifyAll()
    }
}