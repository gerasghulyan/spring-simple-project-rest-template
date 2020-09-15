package com.vntana.core.service.annotation.impl

import com.vntana.core.domain.annotation.Annotation
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.*
import org.junit.Test
import java.util.*

/**
 * Created by Vardan Aivazian
 * Date: 15.09.2020
 * Time: 13:30
 */
class AnnotationUpdateServiceUnitTest : AbstractAnnotationServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { commonTestHelper.buildAnnotationUpdateDto(uuid = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildAnnotationUpdateDto(uuid = emptyString()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildAnnotationUpdateDto(text = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildAnnotationUpdateDto(text = emptyString()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { annotationService.update(null)}
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val annotation = commonTestHelper.buildAnnotation()
        val dto = commonTestHelper.buildAnnotationUpdateDto(annotation.uuid)
        expect(annotationService.findByUuid(dto.uuid)).andReturn(Optional.of(annotation))
        expect(annotationRepository.save(isA(Annotation::class.java))).andAnswer { getCurrentArguments()[0] as Annotation }
        replayAll()
        annotationService.update(dto).let { 
            assertThat(it.uuid).isEqualTo(dto.uuid)
            assertThat(it.text).isEqualTo(dto.text)
            assertThat(it.d1).isEqualTo(dto.d1)
            assertThat(it.d2).isEqualTo(dto.d2)
            assertThat(it.d3).isEqualTo(dto.d3)
        }
        verifyAll()
    }
}