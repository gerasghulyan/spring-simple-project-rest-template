package com.vntana.core.helper.integration.storage.client

import com.vntana.core.domain.client.ClientOrganization
import com.vntana.core.domain.organization.Organization
import com.vntana.core.domain.storage.client.StorageClientOrganizationKey
import com.vntana.core.domain.storage.client.StorageClientOrganizationKeyType
import com.vntana.core.helper.integration.client.ClientOrganizationIntegrationTestHelper
import com.vntana.core.helper.unit.client.ClientOrganizationCommonTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.helper.unit.storage.client.StorageClientOrganizationKeyCommonTestHelper
import com.vntana.core.service.client.ClientOrganizationService
import com.vntana.core.service.organization.OrganizationService
import com.vntana.core.service.storage.client.StorageClientOrganizationKeyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Geras Ghulyan
 * Date: 03-May-21
 * Time: 15:06
 */
@Component
open class StorageClientOrganizationKeyIntegrationTestHelper : StorageClientOrganizationKeyCommonTestHelper() {

    @Autowired
    private lateinit var storageClientOrganizationKeyService: StorageClientOrganizationKeyService

    @Autowired
    protected val clientOrganizationIntegrationTestHelper = ClientOrganizationIntegrationTestHelper()

    fun persistStorageClientOrganizationKey(
            client: ClientOrganization? = clientOrganizationIntegrationTestHelper.persistClientOrganization()
    ): StorageClientOrganizationKey {
        return storageClientOrganizationKeyService.create(
                buildCreateStorageClientOrganizationKeyDto(
                        clientUuid = client?.uuid
                )
        )
    }
}