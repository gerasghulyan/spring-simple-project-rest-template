package com.vntana.core.service.annotation.impl

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Vardan Aivazian
 * Date: 15.09.2020
 * Time: 13:54
 */
class AnnotationFindByUuidServiceUnitTest : AbstractAnnotationServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { annotationService.findByUuid(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { annotationService.findByUuid(emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when not found`() {
        val uuid = uuid()
        resetAll()
        expect(annotationRepository.findByUuid(uuid)).andReturn(Optional.empty())
        replayAll()
        annotationService.findByUuid(uuid).let {
            assertThat(it.isPresent).isFalse()
        }
        verifyAll()
    }

    @Test
    fun test() {
        val annotation = commonTestHelper.buildAnnotation()
        resetAll()
        expect(annotationRepository.findByUuid(annotation.uuid)).andReturn(Optional.of(annotation))
        replayAll()
        annotationService.findByUuid(annotation.uuid).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.get()).isEqualTo(annotation)
        }
        verifyAll()
    }
}