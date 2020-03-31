package com.vntana.core.indexation.producer

import org.easymock.EasyMockRunner
import org.easymock.EasyMockSupport
import org.junit.runner.RunWith
import java.util.*

/**
 * Created by Manuk Gharslyan.
 * Date: 3/25/2020
 * Time: 3:12 PM
 */
@RunWith(EasyMockRunner::class)
abstract class AbstractIndexationProducerUnitTest : EasyMockSupport() {
    fun uuid(): String = UUID.randomUUID().toString()
}