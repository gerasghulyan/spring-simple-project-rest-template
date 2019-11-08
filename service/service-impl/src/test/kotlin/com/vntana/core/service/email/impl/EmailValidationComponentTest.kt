package com.vntana.core.service.email.impl

import com.vntana.core.service.email.AbstractEmailValidationComponentTest
import org.apache.commons.lang3.StringUtils
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test

class EmailValidationComponentTest : AbstractEmailValidationComponentTest() {

    @Test
    fun `test validate correct email`() {
        assertThat(emailValidationComponent.isValid("validemail@mail.com")).isTrue()
        assertThat(emailValidationComponent.isValid("valid888email@mail.com")).isTrue()
        assertThat(emailValidationComponent.isValid("valid__email@mail.com")).isTrue()
    }

    @Test
    fun `test validate null email`() {
        assertThatThrownBy { emailValidationComponent.isValid(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `test validate blank email`() {
        assertThatThrownBy { emailValidationComponent.isValid(StringUtils.EMPTY) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `test validate invalid email`() {
        assertThat(emailValidationComponent.isValid("invalid email")).isFalse()
        assertThat(emailValidationComponent.isValid("validemailmail.com")).isFalse()
        assertThat(emailValidationComponent.isValid("__validemailmail.com")).isFalse()
        assertThat(emailValidationComponent.isValid("#$%validemailmail.com")).isFalse()
    }
}