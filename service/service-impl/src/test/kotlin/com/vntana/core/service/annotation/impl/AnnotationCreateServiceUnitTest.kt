package com.vntana.core.service.annotation.impl

import com.vntana.core.domain.annotation.Annotation
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.*
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 15.09.2020
 * Time: 12:00
 */
class AnnotationCreateServiceUnitTest : AbstractAnnotationServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { commonTestHelper.buildAnnotationCreateDto(userUuid = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildAnnotationCreateDto(userUuid = emptyString()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildAnnotationCreateDto(productUuid = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildAnnotationCreateDto(productUuid = emptyString()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildAnnotationCreateDto(text = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildAnnotationCreateDto(text = emptyString()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildAnnotationCreateDto(number = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildAnnotationCreateDto(number = -1) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildAnnotationCreateDto(number = 0) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildAnnotationCreateDto(d1 = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildAnnotationCreateDto(d2 = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildAnnotationCreateDto(d3 = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { annotationService.create(null)}
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
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
            assertThat(it.d1).isEqualTo(dto.d1)
            assertThat(it.d2).isEqualTo(dto.d2)
            assertThat(it.d3).isEqualTo(dto.d3)
        }
        verifyAll()
    }
}