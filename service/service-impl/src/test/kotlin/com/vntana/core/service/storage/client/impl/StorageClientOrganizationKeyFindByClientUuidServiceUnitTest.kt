package com.vntana.core.service.storage.client.impl

import com.vntana.core.domain.organization.Organization
import com.vntana.core.domain.storage.client.StorageClientOrganizationKey
import com.vntana.core.service.organization.AbstractOrganizationServiceUnitTest
import com.vntana.core.service.storage.client.AbstractStorageClientOrganizationKeyService
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock
import org.easymock.EasyMock.expect
import org.easymock.EasyMock.isA
import org.junit.Test
import java.util.*

/**
 * Created by Geras Ghulyan
 * Date: 03-May-21
 * Time: 15:07
 */
class StorageClientOrganizationKeyFindByClientUuidServiceUnitTest : AbstractStorageClientOrganizationKeyService() {

    @Test
    fun `test findByClientOrganization with invalid arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThatThrownBy { storageClientOrganizationKeyService.findByClientOrganization(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { storageClientOrganizationKeyService.findByClientOrganization(emptyString()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test findByClientOrganization`() {
        // test data
        resetAll()
        val client = clientOrganizationCommonTestHelper.buildClientOrganization()
        val key = storageClientOrganizationKeyCommonTestHelper.buildStorageClientOrganizationKey(client = client)
        // expectations
        expect(clientOrganizationService.getByUuid(client.uuid)).andReturn(client)
        expect(storageClientOrganizationKeyRepository.findByClientOrganizationId(client.id)).andReturn(Optional.of(key))
        replayAll()
        // test scenario
        storageClientOrganizationKeyService.findByClientOrganization(client.uuid).let {
            assertThat(it).isPresent
            assertThat(it.get()).isEqualTo(key)
        }
        verifyAll()
    }

    @Test
    fun `test findByClientOrganization when not found`() {
        // test data
        resetAll()
        val client = clientOrganizationCommonTestHelper.buildClientOrganization()
        // expectations
        expect(clientOrganizationService.getByUuid(client.uuid)).andReturn(client)
        expect(storageClientOrganizationKeyRepository.findByClientOrganizationId(client.id)).andReturn(Optional.empty())
        replayAll()
        // test scenario
        storageClientOrganizationKeyService.findByClientOrganization(client.uuid).let {
            assertThat(it).isNotPresent
        }
        verifyAll()
    }

}