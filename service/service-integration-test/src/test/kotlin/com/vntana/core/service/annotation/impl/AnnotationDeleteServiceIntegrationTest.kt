package com.vntana.core.service.annotation.impl

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 15.09.2020
 * Time: 16:36
 */
class AnnotationDeleteServiceIntegrationTest : AbstractAnnotationServiceIntegrationTest() {

    @Test
    fun `delete test`() {
        val annotation = integrationTestHelper.persistAnnotation()
        flushAndClear()
        annotationService.delete(annotation.uuid).let {
            assertThat(it.removed).isNotNull()
        }
    }
}