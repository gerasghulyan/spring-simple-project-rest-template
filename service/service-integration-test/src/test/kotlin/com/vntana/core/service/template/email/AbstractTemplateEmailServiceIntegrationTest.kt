package com.vntana.core.service.template.email

import com.vntana.core.service.AbstractServiceIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arman Gevorgyan.
 * Date: 10/28/19
 * Time: 1:28 PM
 */
abstract class AbstractTemplateEmailServiceIntegrationTest : AbstractServiceIntegrationTest() {

    @Autowired
    protected lateinit var templateEmailService: TemplateEmailService
}