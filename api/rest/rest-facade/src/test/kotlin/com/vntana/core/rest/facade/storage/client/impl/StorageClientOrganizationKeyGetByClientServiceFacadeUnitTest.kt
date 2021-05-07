package com.vntana.core.rest.facade.storage.client.impl

import com.vntana.core.model.storage.client.error.StorageClientOrganizationKeyErrorResponseModel
import com.vntana.core.rest.facade.storage.client.AbstractStorageClientOrganizationKeyServiceFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Geras Ghulyan
 * Date: 05-May-21
 * Time: 10:10
 */
class StorageClientOrganizationKeyGetByClientServiceFacadeUnitTest : AbstractStorageClientOrganizationKeyServiceFacadeUnitTest() {

    @Test
    fun `test getByClientUuid`() {
        // test data
        val client = clientOrganizationCommonTestHelper.buildClientOrganization()
        val key = storageClientOrganizationKeyCommonTestHelper.buildStorageClientOrganizationKey(client = client)
        resetAll()
        // expectations
        expect(storageClientOrganizationKeyService.findByClientOrganization(client.uuid)).andReturn(Optional.of(key))
        replayAll()
        // test scenario
        storageClientOrganizationKeyServiceFacade.getByClientUuid(client.uuid).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().clientUuid).isEqualTo(client.uuid)
        }
        verifyAll()
    }

    @Test
    fun `test getByClientUuid when not found`() {
        // test data
        val clientUuid = uuid()
        resetAll()
        // expectations
        expect(storageClientOrganizationKeyService.findByClientOrganization(clientUuid)).andReturn(Optional.empty())
        replayAll()
        // test scenario
        assertBasicErrorResultResponse(
                storageClientOrganizationKeyServiceFacade.getByClientUuid(clientUuid),
                StorageClientOrganizationKeyErrorResponseModel.NOT_FOUND
        )
        verifyAll()
    }

}