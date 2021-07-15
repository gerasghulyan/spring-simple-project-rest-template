package com.vntana.core.rest.facade.auth

import com.vntana.core.helper.organization.OrganizationRestTestHelper
import com.vntana.core.helper.unit.client.ClientOrganizationCommonTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.helper.unit.token.personalaccess.TokenPersonalAccessCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.helper.unit.user.role.UserRoleCommonTestHelper
import com.vntana.core.helper.user.UserRestTestHelper
import com.vntana.core.persistence.utils.PersistenceUtilityService
import com.vntana.core.rest.facade.auth.impl.AuthFacadeImpl
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.service.token.personalaccess.PersonalAccessTokenService
import com.vntana.core.service.user.UserService
import com.vntana.core.service.user.role.UserRoleService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 3:58 PM
 */
abstract class AbstractAuthFacadeUnitTest : AbstractFacadeUnitTest() {

    protected lateinit var authFacade: AuthFacade

    @Mock
    protected lateinit var persistenceUtilityService: PersistenceUtilityService

    @Mock
    protected lateinit var userService: UserService

    @Mock
    protected lateinit var userRoleService: UserRoleService

    @Mock
    protected lateinit var personalAccessTokenService: PersonalAccessTokenService

    protected val restHelper = OrganizationRestTestHelper()
    protected val organizationCommonTestHelper = OrganizationCommonTestHelper()
    protected val clientOrganizationCommonTestHelper = ClientOrganizationCommonTestHelper()
    protected val userHelper = UserCommonTestHelper()
    protected val userResourceTestHelper = UserRestTestHelper()
    protected val userRoleCommonTestHelper = UserRoleCommonTestHelper()
    protected val personalAccessCommonTestHelper = TokenPersonalAccessCommonTestHelper()

    @Before
    fun before() {
        authFacade = AuthFacadeImpl(
            userService,
            userRoleService,
            persistenceUtilityService,
            personalAccessTokenService,
            organizationService,
            anonymousUserService
        )
    }
}