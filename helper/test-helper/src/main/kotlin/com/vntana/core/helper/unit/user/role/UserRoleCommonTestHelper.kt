package com.vntana.core.helper.unit.user.role

import com.vntana.core.domain.client.ClientOrganization
import com.vntana.core.domain.organization.Organization
import com.vntana.core.domain.user.User
import com.vntana.core.domain.user.UserClientOrganizationRole
import com.vntana.core.domain.user.UserOrganizationOwnerRole
import com.vntana.core.domain.user.UserRole
import com.vntana.core.helper.AbstractTestHelper
import com.vntana.core.helper.unit.client.ClientOrganizationCommonTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper

/**
 * Created by Arman Gevorgyan.
 * Date: 5/7/20
 * Time: 4:15 PM
 */
class UserRoleCommonTestHelper : AbstractTestHelper() {

    private val userCommonTestHelper = UserCommonTestHelper()
    private val organizationCommonTestHelper = OrganizationCommonTestHelper()
    private val clientOrganizationCommonTestHelper = ClientOrganizationCommonTestHelper()

    fun buildUserOrganizationOwnerRole(user: User? = userCommonTestHelper.buildUser(),
                                       organization: Organization? = organizationCommonTestHelper.buildOrganization()
    ): UserOrganizationOwnerRole = UserOrganizationOwnerRole(user, organization)

    fun buildUserClientOrganizationRole(user: User? = userCommonTestHelper.buildUser(),
                                       clientOrganization: ClientOrganization? = clientOrganizationCommonTestHelper.buildClientOrganization()
    ): UserClientOrganizationRole = UserClientOrganizationRole(user, UserRole.CLIENT_ADMIN, clientOrganization)
}