package com.vntana.core.rest.facade.client.impl

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.persistence.utils.Executable
import com.vntana.core.rest.facade.client.AbstractClientOrganizationServiceFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 11/11/2019
 * Time: 4:33 PM
 */
class ClientOrganizationGetClientOrganizationServiceFacadeUnitTest : AbstractClientOrganizationServiceFacadeUnitTest() {

    @Test
    fun `test getClientOrganizations when organization admin`() {
        // test data
        resetAll()
        val organization = organizationCommonTestHelper.buildOrganization()
        val user = userHelper.buildUser(clientOrganization = organization)
        val clientOrganization = commonTestHelper.buildClientOrganization(organization = organization)
        organization.grantClientOrganization(clientOrganization)

        // expectations
        expect(persistenceUtilityService.runInPersistenceSession(EasyMock.isA(Executable::class.java)))
                .andAnswer { (EasyMock.getCurrentArguments()[0] as Executable).execute() }
        expect(userService.getByUuid(user.uuid)).andReturn(user)
        expect(organizationService.getByUuid(organization.uuid)).andReturn(organization)
        replayAll()
        // test scenario
        clientOrganizationServiceFacade.getUserClientOrganizations(user.uuid, organization.uuid).let {
            assertThat(it.response())
            assertThat(it.response().totalCount()).isEqualTo(1)
            val organizationClientOrganization = it.response().items()[0]
            assertThat(organizationClientOrganization.name).isEqualTo(clientOrganization.name)
            assertThat(organizationClientOrganization.uuid).isEqualTo(clientOrganization.uuid)
            assertThat(organizationClientOrganization.role).isEqualTo(UserRoleModel.ORGANIZATION_ADMIN)
        }
        verifyAll()
    }
}