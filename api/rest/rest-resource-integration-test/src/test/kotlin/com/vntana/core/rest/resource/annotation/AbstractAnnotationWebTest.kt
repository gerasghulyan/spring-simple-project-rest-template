package com.vntana.core.rest.resource.annotation

import com.vntana.core.helper.annotation.AnnotationResourceTestHelper
import com.vntana.core.helper.user.UserResourceTestHelper
import com.vntana.core.rest.client.annotation.AnnotationResourceClient
import com.vntana.core.rest.resource.AbstractWebIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Vardan Aivazian
 * Date: 16.09.2020
 * Time: 18:01
 */
abstract class AbstractAnnotationWebTest : AbstractWebIntegrationTest() {

    @Autowired
    protected lateinit var annotationResourceClient: AnnotationResourceClient
    
    @Autowired
    protected lateinit var resourceTestHelper: AnnotationResourceTestHelper
    
    @Autowired
    protected lateinit var userResourceTestHelper: UserResourceTestHelper
}