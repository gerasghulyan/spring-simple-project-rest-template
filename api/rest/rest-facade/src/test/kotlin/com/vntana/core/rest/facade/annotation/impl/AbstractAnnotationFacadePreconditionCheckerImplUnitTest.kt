package com.vntana.core.rest.facade.annotation.impl

import com.vntana.core.helper.annotation.AnnotationRestTestHelper
import com.vntana.core.helper.unit.annotation.AnnotationCommonTestHelper
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.service.annotation.AnnotationService
import com.vntana.core.service.user.UserService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Vardan Aivazian
 * Date: 16.09.2020
 * Time: 15:15
 */
abstract class AbstractAnnotationFacadePreconditionCheckerImplUnitTest : AbstractFacadeUnitTest() {


    internal lateinit var preconditionChecker: AnnotationFacadePreconditionChecker

    @Mock
    protected lateinit var userService: UserService

    @Mock
    protected lateinit var annotationService: AnnotationService

    protected val commonTestHelper = AnnotationCommonTestHelper()
    protected val restTestHelper = AnnotationRestTestHelper()

    @Before
    fun prepare() {
        preconditionChecker = AnnotationFacadePreconditionCheckerImpl(userService, annotationService)
    }
    
    
}