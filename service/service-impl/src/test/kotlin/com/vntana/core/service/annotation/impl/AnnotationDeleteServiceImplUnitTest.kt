package com.vntana.core.service.annotation.impl

import com.vntana.core.domain.annotation.Annotation
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.*
import org.junit.Test
import java.util.*

/**
 * Created by Vardan Aivazian
 * Date: 15.09.2020
 * Time: 13:45
 */
class AnnotationDeleteServiceImplUnitTest : AbstractAnnotationServiceImplUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertIllegalArgumentException { annotationService.delete(null) }
        assertIllegalArgumentException { annotationService.delete(emptyString()) }
        verifyAll()
    }

    @Test
    fun test() {
        val annotation = commonTestHelper.buildAnnotation()
        resetAll()
        expect(annotationRepository.findByUuid(annotation.uuid)).andReturn(Optional.of(annotation))
        expect(annotationRepository.save(isA(Annotation::class.java))).andAnswer { getCurrentArguments()[0] as Annotation }
        replayAll()
        annotationService.delete(annotation.uuid).let {
            assertThat(it.removed).isNotNull()
        }
        verifyAll()
    }
}