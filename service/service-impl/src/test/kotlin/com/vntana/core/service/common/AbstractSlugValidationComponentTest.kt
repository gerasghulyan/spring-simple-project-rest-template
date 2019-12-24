package com.vntana.core.service.common

import com.vntana.core.service.AbstractServiceUnitTest
import com.vntana.core.service.common.component.SlugValidationComponent
import com.vntana.core.service.common.component.impl.SlugValidationComponentImpl
import org.junit.Before

abstract class AbstractSlugValidationComponentTest : AbstractServiceUnitTest() {
    protected lateinit var slugValidationComponent: SlugValidationComponent

    @Before
    fun prepare() {
        slugValidationComponent = SlugValidationComponentImpl()
    }
}