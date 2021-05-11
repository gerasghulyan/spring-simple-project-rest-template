package com.vntana.core.rest.facade.storage.client.impl

import com.vntana.core.rest.facade.storage.client.AbstractStorageClientOrganizationKeyServiceFacadeUnitTest
import com.vntana.core.service.storage.client.dto.CreateStorageClientOrganizationKeyDto
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Geras Ghulyan
 * Date: 05-May-21
 * Time: 10:10
 */
class StorageClientOrganizationKeyCreateServiceFacadeUnitTest : AbstractStorageClientOrganizationKeyServiceFacadeUnitTest() {

    @Test
    fun `test create`() {
        // test data
        val key = storageClientOrganizationKeyCommonTestHelper.buildStorageClientOrganizationKey()
        val request = storageClientOrganizationKeyRestTestHelper.buildCreateStorageClientOrganizationKeyRequest(clientUuid = key.clientOrganization.uuid)
        val dto = storageClientOrganizationKeyCommonTestHelper.buildCreateStorageClientOrganizationKeyDto(clientUuid = request.clientUuid)
        resetAll()
        // expectations
        expect(storageClientOrganizationKeyService.create(dto)).andReturn(key)
        replayAll()
        // test scenario
        storageClientOrganizationKeyServiceFacade.create(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().name).isNotBlank()
            assertThat(it.response().ring).isNotBlank()
            assertThat(it.response().uuid).isEqualTo(request.clientUuid)
        }
        verifyAll()
    }

}