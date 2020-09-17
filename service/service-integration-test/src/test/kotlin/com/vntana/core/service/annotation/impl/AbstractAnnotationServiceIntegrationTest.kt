package com.vntana.core.service.annotation.impl

import com.vntana.core.helper.integration.annotation.AnnotationIntegrationTestHelper
import com.vntana.core.helper.integration.user.UserIntegrationTestHelper
import com.vntana.core.service.AbstractServiceIntegrationTest
import com.vntana.core.service.annotation.AnnotationService
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Vardan Aivazian
 * Date: 15.09.2020
 * Time: 14:07
 */
abstract class AbstractAnnotationServiceIntegrationTest : AbstractServiceIntegrationTest() {
    
    @Autowired
    protected lateinit var annotationService: AnnotationService

    @Autowired
    protected lateinit var integrationTestHelper : AnnotationIntegrationTestHelper

    @Autowired
    protected lateinit var userIntegrationTestHelper: UserIntegrationTestHelper
}