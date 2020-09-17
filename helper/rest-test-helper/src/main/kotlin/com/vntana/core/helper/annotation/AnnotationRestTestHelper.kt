package com.vntana.core.helper.annotation

import com.vntana.commons.helper.AbstractRestTestHelper
import com.vntana.core.helper.user.UserRestTestHelper
import com.vntana.core.model.annotation.request.CreateAnnotationRequestModel
import com.vntana.core.model.annotation.request.DeleteAnnotationRequestModel
import com.vntana.core.model.annotation.request.FindAnnotationByFilterRequestModel
import com.vntana.core.model.annotation.request.UpdateAnnotationRequestModel
import com.vntana.core.model.annotation.response.AnnotationViewResponseModel
import com.vntana.core.model.user.response.get.model.UserViewResponseModel
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.concurrent.ThreadLocalRandom
import kotlin.random.Random

/**
 * Created by Vardan Aivazian
 * Date: 16.09.2020
 * Time: 15:16
 */
@Component
class AnnotationRestTestHelper : AbstractRestTestHelper() {

    private val userRestTestHelper = UserRestTestHelper()

    fun getRandomInt(min: Int = Integer.MIN_VALUE, max: Int = Integer.MAX_VALUE): Int = (min..max).random()
    
    fun getRandomDouble(min: Double = Double.MIN_VALUE, max: Double = Double.MAX_VALUE): Double = ThreadLocalRandom.current().nextDouble(min, max)
    
    fun buildCreateAnnotationRequestModel(
            userUuid: String? = uuid(),
            productUuid: String? = uuid(),
            text: String? = uuid(),
            number: Int? = getRandomInt(min = 1),
            d1: Double? = getRandomDouble(),
            d2: Double? = getRandomDouble(),
            d3: Double? = getRandomDouble()
    ) = CreateAnnotationRequestModel(userUuid, productUuid, text, number, d1, d2, d3)

    fun buildUpdateAnnotationRequestModel(
            uuid: String? = uuid(),
            userUuid: String? = uuid(),
            text: String? = uuid(),
            resolved: Boolean? = Random.nextBoolean(),
            d1: Double? = getRandomDouble(),
            d2: Double? = getRandomDouble(),
            d3: Double? = getRandomDouble()
    ) = UpdateAnnotationRequestModel(uuid, userUuid, text, resolved, d1, d2, d3)

    fun buildDeleteAnnotationRequestModel(
            uuid: String? = uuid(),
            userUuid: String? = uuid()
    ) = DeleteAnnotationRequestModel(uuid, userUuid)


    fun buildFindAnnotationByFilterRequestModel(
            page: Int = 0,
            size: Int = 10,
            productUuid: String? = uuid()
    ) = FindAnnotationByFilterRequestModel(page, size, productUuid)

    fun buildAnnotationViewResponseModel(uuid: String? = uuid(),
                                         productUuid: String? = uuid(),
                                         text: String? = uuid(),
                                         owner: UserViewResponseModel = userRestTestHelper.buildUserViewResponseModel(),
                                         number: Int? = getRandomInt(min = 1),
                                         resolved: Boolean? = Random.nextBoolean(),
                                         d1: Double? = getRandomDouble(),
                                         d2: Double? = getRandomDouble(),
                                         d3: Double? = getRandomDouble(),
                                         created: LocalDateTime? = LocalDateTime.now(),
                                         updated: LocalDateTime? = LocalDateTime.now()
    ) = AnnotationViewResponseModel(uuid, productUuid, text, owner, number, resolved, d1, d2, d3, created, updated)
}