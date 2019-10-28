package com.vntana.core.service.template.email.test

import com.vntana.core.domain.template.email.TemplateEmailType
import com.vntana.core.service.template.email.AbstractTemplateEmailServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 10/28/19
 * Time: 1:30 PM
 */
class TemplateEmailServiceGetByTypeIntegrationTest : AbstractTemplateEmailServiceIntegrationTest() {

    @Test
    fun `test`() {
        templateEmailService.getByType(TemplateEmailType.FORGOT_PASSWORD).let {
            assertThat(it.type).isEqualTo(TemplateEmailType.FORGOT_PASSWORD)
            assertThat(it.templateName).isNotNull()
        }
    }
}