package com.vntana.core.service.user.external

import com.vntana.core.helper.unit.client.ClientOrganizationCommonTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.persistence.user.UserRepository
import com.vntana.core.persistence.user.extrenal.ExternalUserRepository
import com.vntana.core.service.AbstractServiceUnitTest
import com.vntana.core.service.user.external.impl.ExternalUserServiceImpl
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Diana Gevorgyan
 * Date: 7/13/2021
 * Time: 3:11 PM
 */
abstract class AbstractExternalUserServiceUnitTest : AbstractServiceUnitTest() {

    @Mock
    protected lateinit var repository: ExternalUserRepository

    @Mock
    protected lateinit var userRepository: UserRepository

    protected lateinit var externalUserService: ExternalUserService

    protected val helper = UserCommonTestHelper()

    protected val organizationTestHelper = OrganizationCommonTestHelper()
    
    protected val organizationClientTestHelper = ClientOrganizationCommonTestHelper()

    @Before
    fun beforeTest() {
        externalUserService = ExternalUserServiceImpl(repository, userRepository)
    }
}