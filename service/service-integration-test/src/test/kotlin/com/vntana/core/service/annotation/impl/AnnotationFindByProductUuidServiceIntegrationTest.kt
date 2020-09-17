package com.vntana.core.service.annotation.impl

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 15.09.2020
 * Time: 16:44
 */
class AnnotationFindByProductUuidServiceIntegrationTest : AbstractAnnotationServiceIntegrationTest() {

    @Test
    fun test() {
        val persistAnnotation1 = integrationTestHelper.persistAnnotation()
        val persistAnnotation2 = integrationTestHelper.persistAnnotation(productUuid = persistAnnotation1.productUuid)
        flushAndClear()
        integrationTestHelper.buildAnnotationFindByProductUuidDto(productUuid = persistAnnotation1.productUuid).let {
            annotationService.findByProductUuid(it)
        }.let {
            assertThat(it.totalElements).isEqualTo(2)
            assertThat(it).containsExactly(persistAnnotation1, persistAnnotation2)
        }
    }
}