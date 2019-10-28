package com.vntana.core.service.template.email.test

import com.vntana.core.domain.template.email.TemplateEmail
import com.vntana.core.domain.template.email.TemplateEmailType
import com.vntana.core.service.template.email.AbstractTemplateEmailServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*
import javax.persistence.EntityNotFoundException

/**
 * Created by Arman Gevorgyan.
 * Date: 10/28/19
 * Time: 2:24 PM
 */
class TemplateEmailServiceGetByTypeUnitTest : AbstractTemplateEmailServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { templateEmailService.getByType(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when does not exist`() {
        val type = TemplateEmailType.FORGET_PASSWORD
        resetAll()
        expect(templateEmailRepository.findByType(type)).andReturn(Optional.empty())
        replayAll()
        assertThatThrownBy { templateEmailService.getByType(type) }
                .isExactlyInstanceOf(EntityNotFoundException::class.java)
        verifyAll()
    }

    @Test
    fun `test`() {
        val type = TemplateEmailType.FORGET_PASSWORD
        val templateEmailName = uuid()
        val templateEmail = TemplateEmail(type, templateEmailName)
        resetAll()
        expect(templateEmailRepository.findByType(type)).andReturn(Optional.of(templateEmail))
        replayAll()
        templateEmailService.getByType(type).let {
            assertThat(it).isNotNull
            assertThat(it).isEqualTo(templateEmail)
            assertThat(it.type).isEqualTo(type)
            assertThat(it.templateName).isEqualTo(templateEmailName)
        }
        verifyAll()
    }
}