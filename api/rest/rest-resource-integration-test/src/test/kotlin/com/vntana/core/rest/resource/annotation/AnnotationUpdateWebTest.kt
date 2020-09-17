package com.vntana.core.rest.resource.annotation

import com.vntana.core.model.annotation.AnnotationErrorResponseModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.http.HttpStatus

/**
 * Created by Vardan Aivazian
 * Date: 16.09.2020
 * Time: 19:56
 */
class AnnotationUpdateWebTest : AbstractAnnotationWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                annotationResourceClient.update(resourceTestHelper.buildUpdateAnnotationRequestModel(uuid = null)),
                AnnotationErrorResponseModel.MISSING_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                annotationResourceClient.update(resourceTestHelper.buildUpdateAnnotationRequestModel(uuid = emptyString())),
                AnnotationErrorResponseModel.MISSING_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                annotationResourceClient.update(resourceTestHelper.buildUpdateAnnotationRequestModel(userUuid = null)),
                AnnotationErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                annotationResourceClient.update(resourceTestHelper.buildUpdateAnnotationRequestModel(userUuid = emptyString())),
                AnnotationErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                annotationResourceClient.update(resourceTestHelper.buildUpdateAnnotationRequestModel(text = null)),
                AnnotationErrorResponseModel.MISSING_TEXT
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                annotationResourceClient.update(resourceTestHelper.buildUpdateAnnotationRequestModel(text = emptyString())),
                AnnotationErrorResponseModel.MISSING_TEXT
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                annotationResourceClient.update(resourceTestHelper.buildUpdateAnnotationRequestModel(resolved = null)),
                AnnotationErrorResponseModel.MISSING_RESOLVED
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                annotationResourceClient.update(resourceTestHelper.buildUpdateAnnotationRequestModel(d1 = null)),
                AnnotationErrorResponseModel.MISSING_DIMENSION
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                annotationResourceClient.update(resourceTestHelper.buildUpdateAnnotationRequestModel(d2 = null)),
                AnnotationErrorResponseModel.MISSING_DIMENSION
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                annotationResourceClient.update(resourceTestHelper.buildUpdateAnnotationRequestModel(d3 = null)),
                AnnotationErrorResponseModel.MISSING_DIMENSION
        )
    }

    @Test
    fun `test when annotation not found`() {
        resourceTestHelper.buildUpdateAnnotationRequestModel()
                .let { annotationResourceClient.update(it) }
                .apply {
                    assertBasicErrorResultResponse(
                            HttpStatus.NOT_FOUND,
                            this,
                            AnnotationErrorResponseModel.ANNOTATION_NOT_FOUND
                    )
                }
    }

    @Test
    fun `test user access denied`() {
        val userUuid = userResourceTestHelper.persistUser().response().uuid
        val uuid = resourceTestHelper.persistAnnotation()
        resourceTestHelper.buildUpdateAnnotationRequestModel(uuid = uuid, userUuid = userUuid)
                .let { annotationResourceClient.update(it) }
                .apply {
                    assertBasicErrorResultResponse(
                            HttpStatus.FORBIDDEN,
                            this,
                            AnnotationErrorResponseModel.USER_ACCESS_DENIED
                    )
                }
    }

    @Test
    fun test() {
        val text = uuid()
        val userUuid = userResourceTestHelper.persistUser().response().uuid
        val uuid = resourceTestHelper.persistAnnotation(userUuid = userUuid)
        val d1 = resourceTestHelper.getRandomDouble()
        val d2 = resourceTestHelper.getRandomDouble()
        val d3 = resourceTestHelper.getRandomDouble()
        resourceTestHelper.buildUpdateAnnotationRequestModel(uuid = uuid, userUuid = userUuid, text = text, resolved = true, d1 = d1, d2 = d2, d3 = d3)
                .let { annotationResourceClient.update(it) }
                .apply { assertBasicSuccessResultResponse(this) }
                .body!!.response()
                .apply {
                    assertThat(this.uuid).isEqualTo(uuid)
                    assertThat(this.text).isEqualTo(text)
                    assertThat(this.resolved).isTrue()
                    assertThat(this.d1).isEqualTo(d1)
                    assertThat(this.d2).isEqualTo(d2)
                    assertThat(this.d3).isEqualTo(d3)
                }
    }
}