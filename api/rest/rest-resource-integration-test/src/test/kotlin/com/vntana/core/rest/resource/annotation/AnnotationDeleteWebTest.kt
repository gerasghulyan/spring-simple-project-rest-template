package com.vntana.core.rest.resource.annotation

import com.vntana.core.model.annotation.AnnotationErrorResponseModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.http.HttpStatus

/**
 * Created by Vardan Aivazian
 * Date: 16.09.2020
 * Time: 19:27
 */
class AnnotationDeleteWebTest : AbstractAnnotationWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                annotationResourceClient.delete(resourceTestHelper.buildDeleteAnnotationRequestModel(uuid = null)),
                AnnotationErrorResponseModel.MISSING_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                annotationResourceClient.delete(resourceTestHelper.buildDeleteAnnotationRequestModel(uuid = emptyString())),
                AnnotationErrorResponseModel.MISSING_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                annotationResourceClient.delete(resourceTestHelper.buildDeleteAnnotationRequestModel(uuid = " ")),
                AnnotationErrorResponseModel.MISSING_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                annotationResourceClient.delete(resourceTestHelper.buildDeleteAnnotationRequestModel(userUuid = null)),
                AnnotationErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                annotationResourceClient.delete(resourceTestHelper.buildDeleteAnnotationRequestModel(userUuid = emptyString())),
                AnnotationErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                annotationResourceClient.delete(resourceTestHelper.buildDeleteAnnotationRequestModel(userUuid = " ")),
                AnnotationErrorResponseModel.MISSING_USER_UUID
        )
    }

    @Test
    fun `test when annotation not found`() {
        resourceTestHelper.buildDeleteAnnotationRequestModel()
                .let { annotationResourceClient.delete(it) }
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
        resourceTestHelper.buildDeleteAnnotationRequestModel(uuid = uuid, userUuid = userUuid)
                .let { annotationResourceClient.delete(it) }
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
        val productUuid = uuid()
        val userUuid = userResourceTestHelper.persistUser().response().uuid
        val uuid = resourceTestHelper.persistAnnotation(userUuid = userUuid, productUuid = productUuid)
        resourceTestHelper.buildDeleteAnnotationRequestModel(uuid = uuid, userUuid = userUuid)
                .let { annotationResourceClient.delete(it) }
                .apply { assertBasicSuccessResultResponse(this) }
                .body!!.response().uuid
                .apply { assertThat(this).isEqualTo(uuid) }
                .apply {
                    val existingAnnotationsUuids = resourceTestHelper.buildFindAnnotationByFilterRequestModel(productUuid = productUuid)
                            .let { annotationResourceClient.search(it) }
                            .body!!.response()
                            .items().map { it.uuid }
                    assertThat(existingAnnotationsUuids).doesNotContain(uuid)
                }
    }
}