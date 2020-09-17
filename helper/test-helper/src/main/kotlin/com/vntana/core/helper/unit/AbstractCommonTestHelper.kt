package com.vntana.core.helper.unit

import com.vntana.core.helper.AbstractTestHelper
import org.springframework.data.domain.PageRequest
import java.util.concurrent.ThreadLocalRandom

/**
 * Created by Arthur Asatryan.
 * Date: 10/1/19
 * Time: 3:46 PM
 */
abstract class AbstractCommonTestHelper : AbstractTestHelper() {
    fun buildPageRequest(page: Int = 0, size: Int): PageRequest = PageRequest.of(page, size)
    fun getRandomInt(min: Int = Integer.MIN_VALUE, max: Int = Integer.MAX_VALUE): Int = (min..max).random()
    fun getRandomDouble(min: Double = Double.MIN_VALUE, max: Double = Double.MAX_VALUE): Double = ThreadLocalRandom.current().nextDouble(min, max)
}