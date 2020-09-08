package com.vntana.core.rest.facade.comment.impl

import com.vntana.core.helper.comment.ProductCommentRestTestHelper
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.service.user.UserService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 3:20 PM
 */
abstract class AbstractProductCommentFacadePreconditionCheckerImplUnitTest : AbstractFacadeUnitTest() {

    internal lateinit var preconditionChecker: ProductCommentFacadePreconditionChecker

    @Mock
    protected lateinit var userService: UserService

    protected val restTestHelper = ProductCommentRestTestHelper()

    @Before
    fun prepare() {
        preconditionChecker = ProductCommentFacadePreconditionCheckerImpl(userService)
    }
}