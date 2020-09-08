package com.vntana.core.rest.facade.user

import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.helper.unit.token.TokenCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.helper.unit.user.role.UserRoleCommonTestHelper
import com.vntana.core.helper.user.UserRestTestHelper
import com.vntana.core.persistence.utils.PersistenceUtilityService
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.rest.facade.user.component.UserResetPasswordEmailSenderComponent
import com.vntana.core.rest.facade.user.component.UserVerificationSenderComponent
import com.vntana.core.rest.facade.user.component.precondition.UserFacadePreconditionCheckerComponent
import com.vntana.core.rest.facade.user.impl.UserServiceFacadeImpl
import com.vntana.core.service.email.EmailValidationComponent
import com.vntana.core.service.organization.OrganizationService
import com.vntana.core.service.organization.mediator.OrganizationLifecycleMediator
import com.vntana.core.service.token.TokenService
import com.vntana.core.service.token.auth.AuthTokenService
import com.vntana.core.service.token.reset_password.TokenResetPasswordService
import com.vntana.core.service.user.UserService
import com.vntana.core.service.user.role.UserRoleService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arthur Asatryan.
 * Date: 10/11/19
 * Time: 4:38 PM
 */
abstract class AbstractUserServiceFacadeUnitTest : AbstractFacadeUnitTest() {

    protected val resetPasswordTokenExpirationInMinutes = 100L

    protected lateinit var userServiceFacade: UserServiceFacade

    protected val restHelper = UserRestTestHelper()
    protected val userHelper = UserCommonTestHelper()
    protected val organizationHelper = OrganizationCommonTestHelper()
    protected val userRoleCommonTestHelper = UserRoleCommonTestHelper()
    protected val tokenCommonTestHelper = TokenCommonTestHelper()

    @Mock
    protected lateinit var userService: UserService

    @Mock
    protected lateinit var userRoleService: UserRoleService

    @Mock
    protected lateinit var organizationService: OrganizationService

    @Mock
    protected lateinit var persistenceUtilityService: PersistenceUtilityService

    @Mock
    protected lateinit var preconditionCheckerComponent: UserFacadePreconditionCheckerComponent

    @Mock
    protected lateinit var emailValidationComponent: EmailValidationComponent

    @Mock
    protected lateinit var userVerificationSenderComponent: UserVerificationSenderComponent

    @Mock
    protected lateinit var resetPasswordEmailSenderComponent: UserResetPasswordEmailSenderComponent

    @Mock
    protected lateinit var organizationLifecycleMediator: OrganizationLifecycleMediator

    @Mock
    protected lateinit var tokenService: TokenService

    @Mock
    protected lateinit var tokenResetPasswordService: TokenResetPasswordService

    @Mock
    protected lateinit var authTokenService: AuthTokenService

    @Before
    fun before() {
        userServiceFacade = UserServiceFacadeImpl(userService,
                userRoleService,
                organizationService,
                persistenceUtilityService,
                preconditionCheckerComponent,
                emailValidationComponent,
                userVerificationSenderComponent,
                resetPasswordEmailSenderComponent,
                organizationLifecycleMediator,
                tokenService,
                tokenResetPasswordService,
                authTokenService,
                resetPasswordTokenExpirationInMinutes
        )
    }
}