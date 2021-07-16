package com.vntana.core.rest.resource.storage.client.test

import com.vntana.core.rest.resource.storage.client.AbstractStorageClientOrganizationKeyWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Geras Ghulyan
 * Date: 05-May-21
 * Time: 17:18
 */
class StorageClientOrganizationKeyGetByClientWebTest : AbstractStorageClientOrganizationKeyWebTest() {

    @Test
    fun `test getByClient`() {
        val clientUuid = clientOrganizationResourceTestHelper.persistClientOrganization().response().uuid
        storageClientOrganizationKeyResourceTestHelper.persistStorageClientOrganizationKey(clientUuid = clientUuid)
        storageClientOrganizationKeyResourceClient.getByClientUuid(clientUuid).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.body!!.response().name).isNotBlank()
            assertThat(it.body!!.response().clientUuid).isNotBlank().isEqualTo(clientUuid)
            assertThat(it.body!!.response().ring).isNotBlank()
        }
    }

}