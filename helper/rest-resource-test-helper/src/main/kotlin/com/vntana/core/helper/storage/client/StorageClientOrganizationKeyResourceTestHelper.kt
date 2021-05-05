package com.vntana.core.helper.storage.client

import com.vntana.core.helper.client.ClientOrganizationResourceTestHelper
import com.vntana.core.helper.organization.OrganizationResourceTestHelper
import com.vntana.core.model.client.response.CreateClientOrganizationResultResponse
import com.vntana.core.model.storage.client.response.create.CreateStorageClientOrganizationKeyResultResponse
import com.vntana.core.rest.client.client.ClientOrganizationResourceClient
import com.vntana.core.rest.client.storage.client.StorageClientOrganizationKeyResourceClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

/**
 * Created by Geras Ghulyan
 * Date: 05-May-21
 * Time: 17:18
 */
@Component
class StorageClientOrganizationKeyResourceTestHelper : StorageClientOrganizationKeyRestTestHelper() {

    @Autowired
    private lateinit var storageClientOrganizationKeyResourceClient: StorageClientOrganizationKeyResourceClient

    @Autowired
    private lateinit var clientOrganizationResourceTestHelper: ClientOrganizationResourceTestHelper

    fun persistStorageClientOrganizationKey(
            clientUuid: String? = clientOrganizationResourceTestHelper.persistClientOrganization().response().uuid
    ): ResponseEntity<CreateStorageClientOrganizationKeyResultResponse> {
        val request = buildCreateStorageClientOrganizationKeyRequest(clientUuid = clientUuid)
        return storageClientOrganizationKeyResourceClient.create(request)
    }
}