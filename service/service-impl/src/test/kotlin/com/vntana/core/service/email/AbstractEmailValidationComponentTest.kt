package com.vntana.core.service.email

import com.vntana.core.service.AbstractServiceUnitTest
import com.vntana.core.service.email.impl.EmailValidationComponentImpl
import org.junit.Before

abstract class AbstractEmailValidationComponentTest : AbstractServiceUnitTest() {
    protected lateinit var emailValidationComponent: EmailValidationComponent

    @Before
    fun prepare() {
        emailValidationComponent = EmailValidationComponentImpl()
    }
}