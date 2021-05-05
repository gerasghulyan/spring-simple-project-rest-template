package com.vntana.core.helper.storage.client

import com.vntana.commons.helper.AbstractRestTestHelper
import com.vntana.core.model.storage.client.request.CreateStorageClientOrganizationKeyRequest

/**
 * Created by Geras Ghulyan
 * Date: 03-May-21
 * Time: 10:41
 */
open class StorageClientOrganizationKeyRestTestHelper : AbstractRestTestHelper() {
   
    fun buildCreateStorageClientOrganizationKeyRequest(
            clientUuid: String? = uuid()
    ): CreateStorageClientOrganizationKeyRequest = CreateStorageClientOrganizationKeyRequest(clientUuid)
}