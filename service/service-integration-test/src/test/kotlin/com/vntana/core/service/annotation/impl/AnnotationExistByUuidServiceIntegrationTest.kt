package com.vntana.core.service.annotation.impl

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 15.09.2020
 * Time: 16:38
 */
class AnnotationExistByUuidServiceIntegrationTest : AbstractAnnotationServiceIntegrationTest() {

    @Test
    fun `test when is not found`() {
        assertThat(annotationService.existsByUuid(uuid())).isFalse()
    }

    @Test
    fun test() {
        val annotation = integrationTestHelper.persistAnnotation()
        flushAndClear()
        assertThat(annotationService.existsByUuid(annotation.uuid)).isTrue()
    }
}