package com.vntana.core.service.user.role

import com.vntana.core.helper.unit.user.role.UserRoleCommonTestHelper
import com.vntana.core.persistence.user.role.UserRoleRepository
import com.vntana.core.service.AbstractServiceUnitTest
import com.vntana.core.service.user.role.impl.UserRoleServiceImpl
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arman Gevorgyan.
 * Date: 5/7/20
 * Time: 4:06 PM
 */
abstract class AbstractUserRoleServiceUnitTest : AbstractServiceUnitTest() {

    protected lateinit var userRoleService: UserRoleService

    @Mock
    protected lateinit var userRoleRepository: UserRoleRepository

    protected val userRoleCommonTestHelper = UserRoleCommonTestHelper()

    @Before
    fun prepare() {
        userRoleService = UserRoleServiceImpl(userRoleRepository)
    }
}