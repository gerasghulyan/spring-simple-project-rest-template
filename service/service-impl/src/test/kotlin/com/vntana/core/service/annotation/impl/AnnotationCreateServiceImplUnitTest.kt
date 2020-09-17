package com.vntana.core.service.annotation.impl

import com.vntana.core.domain.annotation.Annotation
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.*
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 15.09.2020
 * Time: 12:00
 */
class AnnotationCreateServiceImplUnitTest : AbstractAnnotationServiceImplUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertIllegalArgumentException { commonTestHelper.buildAnnotationCreateDto(userUuid = null) }
        assertIllegalArgumentException { commonTestHelper.buildAnnotationCreateDto(userUuid = null) }
        assertIllegalArgumentException { commonTestHelper.buildAnnotationCreateDto(userUuid = emptyString()) }
        assertIllegalArgumentException { commonTestHelper.buildAnnotationCreateDto(productUuid = null) }
        assertIllegalArgumentException { commonTestHelper.buildAnnotationCreateDto(productUuid = emptyString()) }
        assertIllegalArgumentException { commonTestHelper.buildAnnotationCreateDto(text = null) }
        assertIllegalArgumentException { commonTestHelper.buildAnnotationCreateDto(text = emptyString()) }
        assertIllegalArgumentException { commonTestHelper.buildAnnotationCreateDto(number = null) }
        assertIllegalArgumentException { commonTestHelper.buildAnnotationCreateDto(number = -1) }
        assertIllegalArgumentException { commonTestHelper.buildAnnotationCreateDto(number = 0) }
        assertIllegalArgumentException { commonTestHelper.buildAnnotationCreateDto(d1 = null) }
        assertIllegalArgumentException { commonTestHelper.buildAnnotationCreateDto(d2 = null) }
        assertIllegalArgumentException { commonTestHelper.buildAnnotationCreateDto(d3 = null) }
        assertIllegalArgumentException { annotationService.create(null) }
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val user = userCommonTestHelper.buildUser()
        val dto = commonTestHelper.buildAnnotationCreateDto(userUuid = user.uuid)
        expect(userService.getByUuid(dto.userUuid)).andReturn(user)
        expect(annotationRepository.save(isA(Annotation::class.java))).andAnswer { getCurrentArguments()[0] as Annotation }
        replayAll()
        annotationService.create(dto).let {
            assertThat(it.user.uuid).isEqualTo(dto.userUuid)
            assertThat(it.productUuid).isEqualTo(dto.productUuid)
            assertThat(it.text).isEqualTo(dto.text)
            assertThat(it.number).isEqualTo(dto.number)
            assertThat(it.resolved).isFalse()
            assertThat(it.d1).isEqualTo(dto.d1)
            assertThat(it.d2).isEqualTo(dto.d2)
            assertThat(it.d3).isEqualTo(dto.d3)
        }
        verifyAll()
    }
}