package com.vntana.core.service.storage.client.impl

import com.vntana.core.domain.storage.client.StorageClientOrganizationKey
import com.vntana.core.domain.storage.client.StorageClientOrganizationKeyType
import com.vntana.core.service.storage.client.AbstractStorageClientOrganizationKeyService
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock
import org.easymock.EasyMock.expect
import org.easymock.EasyMock.isA
import org.junit.Test

/**
 * Created by Geras Ghulyan
 * Date: 03-May-21
 * Time: 15:07
 */
class StorageClientOrganizationKeyCreateServiceUnitTest : AbstractStorageClientOrganizationKeyService() {

    @Test
    fun `test create with invalid arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThatThrownBy { storageClientOrganizationKeyService.create(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { storageClientOrganizationKeyService.create(storageClientOrganizationKeyCommonTestHelper.buildCreateStorageClientOrganizationKeyDto(clientUuid = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { storageClientOrganizationKeyService.create(storageClientOrganizationKeyCommonTestHelper.buildCreateStorageClientOrganizationKeyDto(clientUuid = emptyString())) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test create`() {
        // test data
        resetAll()
        val client = clientOrganizationCommonTestHelper.buildClientOrganization()
        val dto = storageClientOrganizationKeyCommonTestHelper.buildCreateStorageClientOrganizationKeyDto(clientUuid = client.uuid)
        // expectations
        expect(clientOrganizationService.getByUuid(dto.clientUuid)).andReturn(client)
        expect(storageClientOrganizationKeyRepository.save(isA(StorageClientOrganizationKey::class.java)))
                .andAnswer { EasyMock.getCurrentArguments()[0] as StorageClientOrganizationKey }
        replayAll()
        // test scenario
        storageClientOrganizationKeyService.create(dto).let {
            assertThat(it.name).isNotBlank()
            assertThat(it.ring).isNotBlank()
            assertThat(it.clientOrganization).isEqualTo(client)
            assertThat(it.type).isEqualTo(StorageClientOrganizationKeyType.CUSTOMER_MANAGED_KEY)
        }
        verifyAll()
    }

}