package com.vntana.core.service.annotation.impl

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 15.09.2020
 * Time: 14:10
 */
class AnnotationCreateServiceIntegrationTest : AbstractAnnotationServiceIntegrationTest() {

    @Test
    fun `test create`() {
        val dto = integrationTestHelper.buildAnnotationCreateDto(
                userUuid = userIntegrationTestHelper.persistUser().uuid,
                productUuid = uuid()
        )
        annotationService.create(dto).let { 
            flushAndClear()
            assertThat(it.uuid).isNotEmpty()
            assertThat(it.uuid).isNotNull()
            assertThat(it.text).isEqualTo(dto.text)
            assertThat(it.user.uuid).isEqualTo(dto.userUuid)
            assertThat(it.productUuid).isEqualTo(dto.productUuid)
            assertThat(it.number).isEqualTo(dto.number)
            assertThat(it.d1).isEqualTo(dto.d1)
            assertThat(it.d2).isEqualTo(dto.d2)
            assertThat(it.d3).isEqualTo(dto.d3)
        }
    }
}