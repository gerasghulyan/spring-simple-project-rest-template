package com.vntana.core.rest.facade.client.component.precondition

import com.vntana.core.rest.facade.client.component.precondition.impl.ClientOrganizationServiceFacadePreconditionCheckerComponentImpl
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.service.organization.OrganizationService
import com.vntana.core.service.user.UserService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Manuk Gharslyan.
 * Date: 11/18/2020
 * Time: 1:56 PM
 */
abstract class AbstractClientOrganizationServiceFacadePreconditionCheckerUnitTest : AbstractFacadeUnitTest() {
    protected lateinit var preconditionChecker: ClientOrganizationServiceFacadePreconditionCheckerComponent

    @Mock
    protected lateinit var userService: UserService

    @Mock
    protected lateinit var organizationService: OrganizationService

    @Before
    fun prepare() {
        preconditionChecker = ClientOrganizationServiceFacadePreconditionCheckerComponentImpl(userService, organizationService)
    }
}