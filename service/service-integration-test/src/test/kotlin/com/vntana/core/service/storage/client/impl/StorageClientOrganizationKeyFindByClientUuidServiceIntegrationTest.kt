package com.vntana.core.service.storage.client.impl

import com.vntana.core.service.storage.client.AbstractStorageClientOrganizationKeyServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Geras Ghulyan
 * Date: 03-May-21
 * Time: 16:34
 */
class StorageClientOrganizationKeyFindByClientUuidServiceIntegrationTest : AbstractStorageClientOrganizationKeyServiceIntegrationTest() {

    @Test
    fun `test findByClientOrganization`() {
        val key = storageClientOrganizationKeyIntegrationTestHelper.persistStorageClientOrganizationKey()
        storageClientOrganizationKeyService.findByClientOrganization(key.clientOrganization.uuid).let {
            // then
            flushAndClear()
            assertThat(it).isEqualTo(it)
        }
    }

}