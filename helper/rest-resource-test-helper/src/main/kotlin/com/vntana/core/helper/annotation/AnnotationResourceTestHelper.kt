package com.vntana.core.helper.annotation

import com.vntana.core.helper.user.UserResourceTestHelper
import com.vntana.core.rest.client.annotation.AnnotationResourceClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Vardan Aivazian
 * Date: 16.09.2020
 * Time: 18:06
 */
@Component
class AnnotationResourceTestHelper : AnnotationRestTestHelper() {

    @Autowired
    private lateinit var annotationResourceClient: AnnotationResourceClient

    @Autowired
    private lateinit var userResourceTestHelper: UserResourceTestHelper

    fun persistAnnotation(userUuid: String = userResourceTestHelper.persistUser().response().uuid,
                              productUuid: String = uuid(),
                              text: String = uuid(),
                          number: Int? = getRandomInt(min = 1),
                          d1: Double? = getRandomDouble(),
                          d2: Double? = getRandomDouble(),
                          d3: Double? = getRandomDouble()
    ) = buildCreateAnnotationRequestModel(userUuid = userUuid, productUuid = productUuid, text = text, number = number, d1 = d1, d2 = d2, d3 = d3)
            .let { annotationResourceClient.create(it) }
            .body!!.response().uuid
}