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
    fun getRandomInt(): Int = (Integer.MIN_VALUE..Integer.MAX_VALUE).random()
    fun getPositiveRandomInt(): Int = (1..Integer.MAX_VALUE).random()
    fun getRandomDouble(): Double = ThreadLocalRandom.current().nextDouble(Double.MIN_VALUE, Double.MAX_VALUE)
}