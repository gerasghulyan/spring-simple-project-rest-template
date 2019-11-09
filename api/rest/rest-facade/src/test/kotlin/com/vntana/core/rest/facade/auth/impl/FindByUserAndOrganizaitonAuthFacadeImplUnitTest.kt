package com.vntana.core.rest.facade.auth.impl

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.security.request.FindUserByUuidAndOrganizationRequest
import com.vntana.core.model.security.response.model.SecureFindUserByEmailAndOrganizationResponseModel
import com.vntana.core.persistence.utils.Executable
import com.vntana.core.rest.facade.auth.AbstractAuthFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Geras Ghulyan
 * Date: 08.11.19
 * Time: 11:42
 */
class FindByUserAndOrganizaitonAuthFacadeImplUnitTest : AbstractAuthFacadeUnitTest() {

    @Test
    fun `test findByUserAndOrganization`() {
        // test data
        val user = userHelper.buildUser()
        val secureUser = SecureFindUserByEmailAndOrganizationResponseModel(user.uuid, user.email, UserRoleModel.ORGANIZATION_ADMIN, user.password)
        val organization = organizationCommonTestHelper.buildOrganization()
        user.grantOrganizationRole(organization)
        resetAll()
        // expectations
        expect(persistenceUtilityService.runInPersistenceSession(EasyMock.isA(Executable::class.java)))
                .andAnswer { (EasyMock.getCurrentArguments()[0] as Executable).execute() }
        expect(userService.getByUuid(user.uuid)).andReturn(user)
        expect(organizationService.getByUuid(organization.uuid)).andReturn(organization)
        replayAll()
        // test scenario
        val resultResponse = authFacade.findByUserAndOrganization(
                FindUserByUuidAndOrganizationRequest(user.uuid, organization.uuid)
        )
        restHelper.assertBasicSuccessResultResponse(resultResponse)
        assertThat(resultResponse.success()).isTrue()
        assertThat(resultResponse.errors()).isEmpty()
        assertThat(resultResponse.response()).isEqualTo(secureUser)
        verifyAll()
    }
}