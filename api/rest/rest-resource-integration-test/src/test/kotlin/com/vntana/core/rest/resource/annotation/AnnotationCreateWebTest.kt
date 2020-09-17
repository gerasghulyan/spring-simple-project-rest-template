package com.vntana.core.rest.resource.annotation

import com.vntana.core.model.annotation.AnnotationErrorResponseModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.http.HttpStatus

/**
 * Created by Vardan Aivazian
 * Date: 16.09.2020
 * Time: 18:02
 */
class AnnotationCreateWebTest : AbstractAnnotationWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                annotationResourceClient.create(resourceTestHelper.buildCreateAnnotationRequestModel(userUuid = null)),
                AnnotationErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                annotationResourceClient.create(resourceTestHelper.buildCreateAnnotationRequestModel(userUuid = emptyString())),
                AnnotationErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                annotationResourceClient.create(resourceTestHelper.buildCreateAnnotationRequestModel(productUuid = null)),
                AnnotationErrorResponseModel.MISSING_PRODUCT_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                annotationResourceClient.create(resourceTestHelper.buildCreateAnnotationRequestModel(productUuid = emptyString())),
                AnnotationErrorResponseModel.MISSING_PRODUCT_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                annotationResourceClient.create(resourceTestHelper.buildCreateAnnotationRequestModel(text = null)),
                AnnotationErrorResponseModel.MISSING_TEXT
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                annotationResourceClient.create(resourceTestHelper.buildCreateAnnotationRequestModel(text = emptyString())),
                AnnotationErrorResponseModel.MISSING_TEXT
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                annotationResourceClient.create(resourceTestHelper.buildCreateAnnotationRequestModel(number = null)),
                AnnotationErrorResponseModel.MISSING_NUMBER
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                annotationResourceClient.create(resourceTestHelper.buildCreateAnnotationRequestModel(number = -100)),
                AnnotationErrorResponseModel.NOT_POSITIVE_NUMBER
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                annotationResourceClient.create(resourceTestHelper.buildCreateAnnotationRequestModel(d1 = null)),
                AnnotationErrorResponseModel.MISSING_DIMENSION
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                annotationResourceClient.create(resourceTestHelper.buildCreateAnnotationRequestModel(d2 = null)),
                AnnotationErrorResponseModel.MISSING_DIMENSION
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                annotationResourceClient.create(resourceTestHelper.buildCreateAnnotationRequestModel(d3 = null)),
                AnnotationErrorResponseModel.MISSING_DIMENSION
        )
    }

    @Test
    fun `test when user not found`() {
        resourceTestHelper.buildCreateAnnotationRequestModel()
                .let { annotationResourceClient.create(it) }
                .apply {
                    assertBasicErrorResultResponse(
                            HttpStatus.NOT_FOUND,
                            this,
                            AnnotationErrorResponseModel.USER_NOT_FOUND
                    )
                }
    }
    
    @Test
    fun test() {
        userResourceTestHelper.persistUser().response().uuid
                .let { resourceTestHelper.buildCreateAnnotationRequestModel(userUuid = it) }
                .let { annotationResourceClient.create(it) }
                .apply { assertBasicSuccessResultResponse(this) }
                .body!!.response()
                .apply { assertThat(this.uuid).isNotNull() }
    }
}