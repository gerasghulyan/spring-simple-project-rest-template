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
import java.util.concurrent.ThreadLocalRandom

/**
 * Created by Vardan Aivazian
 * Date: 16.09.2020
 * Time: 15:16
 */
@Component
class AnnotationRestTestHelper : AbstractRestTestHelper() {

    private val userRestTestHelper = UserRestTestHelper()

    fun getRandomInt(): Int = (Integer.MIN_VALUE..Integer.MAX_VALUE).random()
    
    fun getPositiveRandomInt(): Int = (1..Integer.MAX_VALUE).random()
    
    fun getRandomDouble(): Double = ThreadLocalRandom.current().nextDouble(Double.MIN_VALUE, Double.MAX_VALUE)

    fun buildCreateAnnotationRequestModel(
            userUuid: String? = uuid(),
            productUuid: String? = uuid(),
            text: String? = uuid(),
            number: Int? = getPositiveRandomInt(),
            d1: Double? = getRandomDouble(),
            d2: Double? = getRandomDouble(),
            d3: Double? = getRandomDouble()
    ) = CreateAnnotationRequestModel(userUuid, productUuid, text, number, d1, d2, d3)

    fun buildUpdateAnnotationRequestModel(
            uuid: String? = uuid(),
            userUuid: String? = uuid(),
            text: String? = uuid(),
            d1: Double? = getRandomDouble(),
            d2: Double? = getRandomDouble(),
            d3: Double? = getRandomDouble()
    ) = UpdateAnnotationRequestModel(uuid, userUuid, text, d1, d2, d3)

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
                                         number: Int? = getPositiveRandomInt(),
                                         d1: Double? = getRandomDouble(),
                                         d2: Double? = getRandomDouble(),
                                         d3: Double? = getRandomDouble()
    ) = AnnotationViewResponseModel(uuid, productUuid, text, owner, number, d1, d2, d3)
}