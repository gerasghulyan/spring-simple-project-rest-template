package com.vntana.core.rest.facade.comment.impl

import com.vntana.core.helper.comment.ProductCommentRestTestHelper
import com.vntana.core.helper.unit.comment.product.ProductCommentCommonTestHelper
import com.vntana.core.rest.facade.comment.ProductCommentFacade
import com.vntana.core.rest.facade.comment.builder.ProductCommentViewModelBuilder
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.service.comment.product.ProductCommentService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 3:19 PM
 */
abstract class AbstractProductCommentFacadeImplUnitTest : AbstractFacadeUnitTest() {

    protected lateinit var productCommentFacade: ProductCommentFacade

    @Mock
    internal lateinit var preconditionChecker: ProductCommentFacadePreconditionChecker

    @Mock
    protected lateinit var productCommentService: ProductCommentService

    @Mock
    protected lateinit var productCommentViewModelBuilder: ProductCommentViewModelBuilder

    protected val commonTestHelper = ProductCommentCommonTestHelper()
    protected val restTestHelper = ProductCommentRestTestHelper()

    @Before
    fun prepare() {
        productCommentFacade = ProductCommentFacadeImpl(
                preconditionChecker,
                productCommentService,
                productCommentViewModelBuilder
        )
    }
}