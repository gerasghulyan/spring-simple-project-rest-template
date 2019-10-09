package com.vntana.core.service

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
}