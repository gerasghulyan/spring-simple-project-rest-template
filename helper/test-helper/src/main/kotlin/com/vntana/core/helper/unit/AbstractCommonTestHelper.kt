package com.vntana.core.helper.unit

import com.vntana.core.helper.AbstractTestHelper
import org.springframework.data.domain.PageRequest

/**
 * Created by Arthur Asatryan.
 * Date: 10/1/19
 * Time: 3:46 PM
 */
abstract class AbstractCommonTestHelper : AbstractTestHelper() {
    fun buildPageRequest(page: Int = 0, size: Int): PageRequest = PageRequest.of(page, size)
}