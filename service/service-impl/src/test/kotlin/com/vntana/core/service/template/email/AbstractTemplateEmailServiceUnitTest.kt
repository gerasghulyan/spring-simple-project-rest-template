package com.vntana.core.service.template.email

import com.vntana.core.persistence.template.email.TemplateEmailRepository
import com.vntana.core.service.AbstractServiceUnitTest
import com.vntana.core.service.template.email.impl.TemplateEmailServiceImpl
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arman Gevorgyan.
 * Date: 10/28/19
 * Time: 2:22 PM
 */
abstract class AbstractTemplateEmailServiceUnitTest : AbstractServiceUnitTest() {

    lateinit var templateEmailService: TemplateEmailService

    @Mock
    lateinit var templateEmailRepository: TemplateEmailRepository

    @Before
    fun prepare() {
        templateEmailService = TemplateEmailServiceImpl(templateEmailRepository)
    }
}