package com.vntana.core.helper.unit.annotation

import com.vntana.core.domain.annotation.Annotation
import com.vntana.core.domain.user.User
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.service.annotation.dto.AnnotationCreateDto
import com.vntana.core.service.annotation.dto.AnnotationFindByProductUuidDto
import com.vntana.core.service.annotation.dto.AnnotationUpdateDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

/**
 * Created by Vardan Aivazian
 * Date: 15.09.2020
 * Time: 11:53
 */
open class AnnotationCommonTestHelper : AbstractCommonTestHelper() {

    private val userCommonTestHelper = UserCommonTestHelper()

    fun buildAnnotation(
            productUuid: String? = uuid(),
            user: User = userCommonTestHelper.buildUser(),
            text: String? = uuid(),
            number: Int = getPositiveRandomInt(),
            d1: Double? = getRandomDouble(),
            d2: Double? = getRandomDouble(),
            d3: Double? = getRandomDouble()
    ): Annotation = Annotation(productUuid, user, text, number, d1, d2, d3)
    
    fun buildAnnotationCreateDto(
            userUuid: String? = uuid(),
            productUuid: String? = uuid(),
            text: String? = uuid(),
            number: Int? = getPositiveRandomInt(),
            d1: Double? = getRandomDouble(),
            d2: Double? = getRandomDouble(),
            d3: Double? = getRandomDouble()
    ): AnnotationCreateDto = AnnotationCreateDto(userUuid, productUuid, text, number, d1, d2, d3)
    
    fun buildAnnotationUpdateDto(
            uuid: String? = uuid(),
            text: String? = uuid(),
            d1: Double? = getRandomDouble(),
            d2: Double? = getRandomDouble(),
            d3: Double? = getRandomDouble()
    ): AnnotationUpdateDto = AnnotationUpdateDto(uuid, text, d1, d2, d3)

    fun buildAnnotationPage(
            productUuid: String? = uuid(),
            entities: List<Annotation> = listOf(buildAnnotation(productUuid = productUuid), buildAnnotation(productUuid = productUuid)),
            total: Long = entities.size.toLong(),
            pageAble: Pageable = buildPageRequest(0, 5)
    ): Page<Annotation> = PageImpl(entities, pageAble, total)

    fun buildAnnotationFindByProductUuidDto(
            page: Int = 0,
            size: Int = 5,
            productUuid: String? = uuid()
    ): AnnotationFindByProductUuidDto = AnnotationFindByProductUuidDto(page, size, productUuid)
}