package com.vntana.core.service.annotation.impl

import com.vntana.commons.service.exception.EntityNotFoundForUuidException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 15.09.2020
 * Time: 16:41
 */
class AnnotationGetByUuidServiceIntegrationTest : AbstractAnnotationServiceIntegrationTest() {
    
    @Test
    fun `test when not found`() {
        val uuid = uuid()
        flushAndClear()
        assertThatThrownBy { annotationService.getByUuid(uuid) }
                .isExactlyInstanceOf(EntityNotFoundForUuidException::class.java)
    }
    
    @Test
    fun test() {
        val annotation = integrationTestHelper.persistAnnotation()
        flushAndClear()
        annotationService.getByUuid(annotation.uuid).let {
            assertThat(it).isEqualTo(annotation)
        }
    }
}