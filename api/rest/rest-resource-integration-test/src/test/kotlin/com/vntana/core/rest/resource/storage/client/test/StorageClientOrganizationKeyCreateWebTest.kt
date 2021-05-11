package com.vntana.core.rest.resource.storage.client.test

import com.vntana.core.model.storage.client.error.StorageClientOrganizationKeyErrorResponseModel
import com.vntana.core.rest.resource.storage.client.AbstractStorageClientOrganizationKeyWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Geras Ghulyan
 * Date: 05-May-21
 * Time: 17:18
 */
class StorageClientOrganizationKeyCreateWebTest : AbstractStorageClientOrganizationKeyWebTest() {

    @Test
    fun `test create with invalid arguments`() {
        assertBasicErrorResultResponse(
                storageClientOrganizationKeyResourceClient.create(
                        storageClientOrganizationKeyResourceTestHelper.buildCreateStorageClientOrganizationKeyRequest(
                                clientUuid = null
                        )
                ),
                StorageClientOrganizationKeyErrorResponseModel.MISSING_CLIENT_UUID
        )
        assertBasicErrorResultResponse(
                storageClientOrganizationKeyResourceClient.create(
                        storageClientOrganizationKeyResourceTestHelper.buildCreateStorageClientOrganizationKeyRequest(
                                clientUuid = emptyString()
                        )
                ),
                StorageClientOrganizationKeyErrorResponseModel.MISSING_CLIENT_UUID
        )
    }

    @Test
    fun `test create`() {
        val clientUuid = clientOrganizationResourceTestHelper.persistClientOrganization().response().uuid
        val request = storageClientOrganizationKeyResourceTestHelper.buildCreateStorageClientOrganizationKeyRequest(clientUuid = clientUuid)
        storageClientOrganizationKeyResourceClient.create(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.body.response().name).isNotBlank()
            assertThat(it.body.response().ring).isNotBlank()
            assertThat(it.body.response().uuid).isNotBlank().isEqualTo(clientUuid)
        }
    }

}