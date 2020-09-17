package com.vntana.core.service.annotation.impl

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 15.09.2020
 * Time: 16:40
 */
class AnnotationFindByUuidServiceIntegrationTest : AbstractAnnotationServiceIntegrationTest() {

    @Test
    fun test() {
        val annotation = integrationTestHelper.persistAnnotation()
        flushAndClear()
        annotationService.findByUuid(annotation.uuid).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.get()).isEqualTo(annotation)
        }
    }
}