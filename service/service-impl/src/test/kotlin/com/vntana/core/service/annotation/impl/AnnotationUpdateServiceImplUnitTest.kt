package com.vntana.core.service.annotation.impl

import com.vntana.core.domain.annotation.Annotation
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.*
import org.junit.Test
import java.util.*

/**
 * Created by Vardan Aivazian
 * Date: 15.09.2020
 * Time: 13:30
 */
class AnnotationUpdateServiceImplUnitTest : AbstractAnnotationServiceImplUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertIllegalArgumentException { commonTestHelper.buildAnnotationUpdateDto(uuid = null) }
        assertIllegalArgumentException { commonTestHelper.buildAnnotationUpdateDto(uuid = emptyString()) }
        assertIllegalArgumentException { commonTestHelper.buildAnnotationUpdateDto(text = null) }
        assertIllegalArgumentException { commonTestHelper.buildAnnotationUpdateDto(text = emptyString()) }
        assertIllegalArgumentException { commonTestHelper.buildAnnotationUpdateDto(resolved = null) }
        assertIllegalArgumentException { commonTestHelper.buildAnnotationUpdateDto(d1 = null) }
        assertIllegalArgumentException { commonTestHelper.buildAnnotationUpdateDto(d2 = null) }
        assertIllegalArgumentException { commonTestHelper.buildAnnotationUpdateDto(d3 = null) }
        assertIllegalArgumentException { annotationService.update(null) }
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
            assertThat(it.resolved).isEqualTo(dto.resolved)
            assertThat(it.d1).isEqualTo(dto.d1)
            assertThat(it.d2).isEqualTo(dto.d2)
            assertThat(it.d3).isEqualTo(dto.d3)
        }
        verifyAll()
    }
}