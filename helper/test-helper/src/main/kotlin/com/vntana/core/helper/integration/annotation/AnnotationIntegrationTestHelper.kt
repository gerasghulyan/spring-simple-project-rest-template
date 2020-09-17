package com.vntana.core.helper.integration.annotation

import com.vntana.core.domain.annotation.Annotation
import com.vntana.core.helper.integration.user.UserIntegrationTestHelper
import com.vntana.core.helper.unit.annotation.AnnotationCommonTestHelper
import com.vntana.core.service.annotation.AnnotationService
import com.vntana.core.service.annotation.dto.AnnotationCreateDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Vardan Aivazian
 * Date: 15.09.2020
 * Time: 14:08
 */
@Component
class AnnotationIntegrationTestHelper : AnnotationCommonTestHelper() {
    
    @Autowired
    private lateinit var annotationService: AnnotationService

    @Autowired
    private lateinit var userIntegrationTestHelper: UserIntegrationTestHelper
    
    fun persistAnnotation(
            userUuid: String? = userIntegrationTestHelper.persistUser().uuid,
            productUuid: String? = uuid(),
            text: String? = uuid(),
            number: Int? = getRandomInt(min = 1),
            d1: Double? = getRandomDouble(),
            d2: Double? = getRandomDouble(),
            d3: Double? = getRandomDouble()
    ): Annotation {
        val dto: AnnotationCreateDto = buildAnnotationCreateDto(userUuid = userUuid, productUuid = productUuid, text = text, number = number, d1 = d1, d2 = d2, d3 = d3)
        return annotationService.create(dto)
    }
}