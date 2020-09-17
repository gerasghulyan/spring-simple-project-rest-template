package com.vntana.core.rest.resource.annotation

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 16.09.2020
 * Time: 19:48
 */
class AnnotationFindByFilterWebTest : AbstractAnnotationWebTest() {

    @Test
    fun `test when not found`() {
        resourceTestHelper.buildFindAnnotationByFilterRequestModel()
                .let { annotationResourceClient.search(it) }
                .apply { assertBasicSuccessResultResponse(this) }
                .body!!.response()
                .apply { assertThat(this.totalCount()).isEqualTo(0) }
                .apply { assertThat(this.items()).isEmpty() }
    }

    @Test
    fun test() {
        val userFullName = uuid()
        val userUuid = userResourceTestHelper.persistUser(
                userResourceTestHelper.buildCreateUserRequest(fullName = userFullName)
        ).response().uuid
        val productUuid = uuid()
        val text = uuid()
        val number = resourceTestHelper.getPositiveRandomInt()
        val d1 = resourceTestHelper.getRandomDouble()
        val d2 = resourceTestHelper.getRandomDouble()
        val d3 = resourceTestHelper.getRandomDouble()
        resourceTestHelper.persistAnnotation()
        resourceTestHelper.persistAnnotation(userUuid = userUuid, productUuid = productUuid, text = text, number = number, d1 = d1, d2 = d2, d3 = d3)
        resourceTestHelper.buildFindAnnotationByFilterRequestModel(productUuid = productUuid)
                .let { annotationResourceClient.search(it) }
                .apply { assertBasicSuccessResultResponse(this) }
                .body!!.response()
                .apply { assertThat(this.totalCount()).isEqualTo(1) }
                .items()
                .apply { assertThat(this.size).isEqualTo(1) }[0]
                .apply { assertThat(this.productUuid).isEqualTo(productUuid) }
                .apply { assertThat(this.text).isEqualTo(text) }
                .apply { assertThat(this.number).isEqualTo(number) }
                .apply { assertThat(this.d1).isEqualTo(d1) }
                .apply { assertThat(this.d2).isEqualTo(d2) }
                .apply { assertThat(this.d3).isEqualTo(d3) }
                .owner
                .apply { assertThat(this.fullName).isEqualTo(userFullName) }
                .apply { assertThat(this.uuid).isEqualTo(userUuid) }
    }
}