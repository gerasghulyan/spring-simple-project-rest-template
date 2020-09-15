package com.vntana.core.service.annotation.impl

import com.vntana.core.helper.unit.annotation.AnnotationCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.persistence.annotation.AnnotationRepository
import com.vntana.core.service.AbstractServiceUnitTest
import com.vntana.core.service.annotation.AnnotationService
import com.vntana.core.service.user.UserService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Vardan Aivazian
 * Date: 15.09.2020
 * Time: 11:52
 */
abstract class AbstractAnnotationServiceUnitTest : AbstractServiceUnitTest() {
    
    protected lateinit var annotationService: AnnotationService
    
    protected val commonTestHelper = AnnotationCommonTestHelper()
    protected val userCommonTestHelper = UserCommonTestHelper()
    
    @Mock
    protected lateinit var userService: UserService
    
    @Mock
    protected lateinit var annotationRepository: AnnotationRepository
    
    @Before
    fun prepare() {
        annotationService = AnnotationServiceImpl(annotationRepository, userService)
    }
}