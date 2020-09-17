package com.vntana.core.rest.facade.annotation.impl

import com.vntana.core.helper.annotation.AnnotationRestTestHelper
import com.vntana.core.helper.unit.annotation.AnnotationCommonTestHelper
import com.vntana.core.rest.facade.annotation.AnnotationFacade
import com.vntana.core.rest.facade.annotation.builder.AnnotationViewModelBuilder
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.service.annotation.AnnotationService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Vardan Aivazian
 * Date: 16.09.2020
 * Time: 15:12
 */
abstract class AbstractAnnotationFacadeImplUnitTest : AbstractFacadeUnitTest() {

    protected lateinit var annotationFacade: AnnotationFacade

    @Mock
    protected lateinit var preconditionChecker: AnnotationFacadePreconditionChecker
    
    @Mock
    protected lateinit var annotationService: AnnotationService
    
    @Mock
    protected lateinit var annotationViewModelBuilder: AnnotationViewModelBuilder

    protected val restTestHelper = AnnotationRestTestHelper()
    protected val commonTestHelper = AnnotationCommonTestHelper()

    @Before
    fun prepare() {
        annotationFacade = AnnotationFacadeImpl(preconditionChecker, annotationService, annotationViewModelBuilder)
    }
}