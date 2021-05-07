package com.vntana.core.helper.unit.storage.client

import com.vntana.core.domain.client.ClientOrganization
import com.vntana.core.domain.invitation.organization.InvitationOrganization
import com.vntana.core.domain.organization.Organization
import com.vntana.core.domain.organization.status.OrganizationStatus
import com.vntana.core.domain.storage.client.StorageClientOrganizationKey
import com.vntana.core.domain.storage.client.StorageClientOrganizationKeyType
import com.vntana.core.domain.user.UserRole
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import com.vntana.core.helper.unit.client.ClientOrganizationCommonTestHelper
import com.vntana.core.helper.unit.invitation.organization.InvitationOrganizationCommonTestHelper
import com.vntana.core.service.organization.dto.*
import com.vntana.core.service.storage.client.dto.CreateStorageClientOrganizationKeyDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

/**
 * Created by Geras Ghulyan
 * Date: 03-May-21
 * Time: 15:06
 */
open class StorageClientOrganizationKeyCommonTestHelper : AbstractCommonTestHelper() {

    protected val clientOrganizationCommonTestHelper = ClientOrganizationCommonTestHelper()

    fun buildCreateStorageClientOrganizationKeyDto(
            clientUuid: String? = uuid()
    ): CreateStorageClientOrganizationKeyDto = CreateStorageClientOrganizationKeyDto(clientUuid)

    fun buildStorageClientOrganizationKey(
            client: ClientOrganization? = clientOrganizationCommonTestHelper.buildClientOrganization(),
            name: String? = uuid(),
            type: StorageClientOrganizationKeyType? = StorageClientOrganizationKeyType.CUSTOMER_MANAGED_KEY
    ): StorageClientOrganizationKey = StorageClientOrganizationKey(client, name, type)
}