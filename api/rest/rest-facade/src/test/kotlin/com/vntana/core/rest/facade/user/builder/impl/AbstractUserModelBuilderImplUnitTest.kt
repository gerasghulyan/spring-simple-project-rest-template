package com.vntana.core.rest.facade.user.builder.impl

import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.rest.facade.user.builder.UserModelBuilder
import com.vntana.core.service.user.UserService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 3:54 PM
 */
abstract class AbstractUserModelBuilderImplUnitTest : AbstractFacadeUnitTest() {

    protected lateinit var builder: UserModelBuilder

    @Mock
    protected lateinit var userService: UserService

    protected val commonTestHelper = UserCommonTestHelper()

    @Before
    fun prepare() {
        builder = UserModelBuilderImpl(userService)
    }
}