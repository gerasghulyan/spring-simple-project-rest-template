package com.vntana.core.service.annotation.impl

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 15.09.2020
 * Time: 15:53
 */
class AnnotationUpdateServiceIntegrationTest : AbstractAnnotationServiceIntegrationTest() {

    @Test
    fun `test update`() {
        val annotation = integrationTestHelper.persistAnnotation()
        val dto = integrationTestHelper.buildAnnotationUpdateDto(annotation.uuid)
        flushAndClear()
        annotationService.update(dto).let {
            assertThat(it.uuid).isEqualTo(annotation.uuid)
            assertThat(it.text).isEqualTo(dto.text)
            assertThat(it.d1).isEqualTo(dto.d1)
            assertThat(it.d2).isEqualTo(dto.d2)
            assertThat(it.d3).isEqualTo(dto.d3)
        }
    }
}