package com.vntana.core.service.storage.client.impl

import com.vntana.core.domain.storage.client.StorageClientOrganizationKeyType
import com.vntana.core.service.storage.client.AbstractStorageClientOrganizationKeyServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Geras Ghulyan
 * Date: 03-May-21
 * Time: 16:34
 */
class StorageClientOrganizationKeyCreateServiceIntegrationTest : AbstractStorageClientOrganizationKeyServiceIntegrationTest() {

    @Test
    fun `test create`() {
        val client = clientOrganizationIntegrationTestHelper.persistClientOrganization()
        // given
        storageClientOrganizationKeyIntegrationTestHelper.buildCreateStorageClientOrganizationKeyDto(clientUuid = client.uuid).let { dto ->
            // when
            storageClientOrganizationKeyService.create(dto).let { key ->
                // then
                flushAndClear()
                assertThat(key.name).isNotBlank()
                assertThat(key.ring).isNotBlank()
                assertThat(key.clientOrganization).isEqualTo(client)
                assertThat(key.type).isEqualTo(StorageClientOrganizationKeyType.CUSTOMER_MANAGED_KEY)
            }
        }
    }

}