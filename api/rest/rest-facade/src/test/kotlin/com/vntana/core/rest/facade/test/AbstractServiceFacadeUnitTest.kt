package com.vntana.core.rest.facade.test

import org.easymock.EasyMockRunner
import org.easymock.EasyMockSupport
import org.junit.runner.RunWith
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 3:55 PM
 */
@RunWith(EasyMockRunner::class)
abstract class AbstractServiceFacadeUnitTest : EasyMockSupport() {
    fun uuid(): String = UUID.randomUUID().toString()
}