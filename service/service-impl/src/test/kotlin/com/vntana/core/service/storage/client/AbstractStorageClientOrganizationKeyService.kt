package com.vntana.core.service.storage.client

import com.vntana.core.helper.unit.client.ClientOrganizationCommonTestHelper
import com.vntana.core.helper.unit.storage.client.StorageClientOrganizationKeyCommonTestHelper
import com.vntana.core.persistence.storage.client.StorageClientOrganizationKeyRepository
import com.vntana.core.service.AbstractServiceUnitTest
import com.vntana.core.service.client.ClientOrganizationService
import com.vntana.core.service.organization.impl.OrganizationServiceImpl
import com.vntana.core.service.storage.client.impl.StorageClientOrganizationKeyServiceImpl
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Geras Ghulyan
 * Date: 03-May-21
 * Time: 15:06
 */
abstract class AbstractStorageClientOrganizationKeyService : AbstractServiceUnitTest() {

    @Mock
    protected lateinit var storageClientOrganizationKeyRepository: StorageClientOrganizationKeyRepository

    @Mock
    protected lateinit var clientOrganizationService: ClientOrganizationService

    protected lateinit var storageClientOrganizationKeyService: StorageClientOrganizationKeyService

    protected var storageClientOrganizationKeyCommonTestHelper = StorageClientOrganizationKeyCommonTestHelper()

    protected var clientOrganizationCommonTestHelper = ClientOrganizationCommonTestHelper()

    @Before
    fun before() {
        storageClientOrganizationKeyService = StorageClientOrganizationKeyServiceImpl(storageClientOrganizationKeyRepository, clientOrganizationService)
    }
}