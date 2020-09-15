package com.vntana.core.service.annotation.impl

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import org.springframework.data.domain.PageRequest

/**
 * Created by Vardan Aivazian
 * Date: 15.09.2020
 * Time: 12:50
 */
class AnnotationFindByProductUuidServiceUnitTest : AbstractAnnotationServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { commonTestHelper.buildAnnotationFindByProductUuidDto(productUuid = null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildAnnotationFindByProductUuidDto(productUuid = emptyString()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { annotationService.findByProductUuid(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val page = 0
        val size = 5
        val productUuid = uuid()
        val annotationPage = commonTestHelper.buildAnnotationPage(productUuid = productUuid)
        val dto = commonTestHelper.buildAnnotationFindByProductUuidDto(page = page, size = size, productUuid = productUuid)
        expect(annotationRepository.findByProductUuidAndRemovedIsNull(productUuid, PageRequest.of(page, size))).andReturn(annotationPage)
        replayAll()
        assertThat(annotationService.findByProductUuid(dto)).isEqualTo(annotationPage)
        verifyAll()
    }
}