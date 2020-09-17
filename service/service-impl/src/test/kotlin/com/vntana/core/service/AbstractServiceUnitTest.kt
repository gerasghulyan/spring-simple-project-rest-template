package com.vntana.core.service

import org.apache.commons.lang3.StringUtils
import org.assertj.core.api.Assertions
import org.easymock.EasyMockRunner
import org.easymock.EasyMockSupport
import org.junit.runner.RunWith
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 10/1/19
 * Time: 3:07 PM
 */
@RunWith(EasyMockRunner::class)
abstract class AbstractServiceUnitTest : EasyMockSupport() {
    fun uuid(): String = UUID.randomUUID().toString()
    fun emptyString(): String = StringUtils.EMPTY
    fun email(): String = "${uuid()}@mail.com"
    fun assertIllegalArgumentException(action: Action) {
        Assertions.assertThatThrownBy { action.invoke() }.isExactlyInstanceOf(IllegalArgumentException::class.java)
    }
}

typealias Action = () -> Any