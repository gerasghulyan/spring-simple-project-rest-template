package com.vntana.core.service.annotation.impl

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 15.09.2020
 * Time: 13:51
 */
class AnnotationExistByUuidServiceUnitTest : AbstractAnnotationServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { annotationService.existsByUuid(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { annotationService.existsByUuid(emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when does not exist`() {
        val uuid = uuid()
        resetAll()
        expect(annotationService.existsByUuid(uuid)).andReturn(false)
        replayAll()
        assertThat(annotationService.existsByUuid(uuid)).isFalse()
        verifyAll()
    }

    @Test
    fun test() {
        val uuid = uuid()
        resetAll()
        expect(annotationService.existsByUuid(uuid)).andReturn(true)
        replayAll()
        assertThat(annotationService.existsByUuid(uuid)).isTrue()
        verifyAll()
    }
}