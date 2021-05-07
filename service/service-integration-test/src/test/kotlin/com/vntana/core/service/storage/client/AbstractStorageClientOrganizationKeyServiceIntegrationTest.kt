package com.vntana.core.service.storage.client

import com.vntana.core.helper.integration.client.ClientOrganizationIntegrationTestHelper
import com.vntana.core.helper.integration.storage.client.StorageClientOrganizationKeyIntegrationTestHelper
import com.vntana.core.service.AbstractServiceIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Geras Ghulyan
 * Date: 03-May-21
 * Time: 16:33
 */
abstract class AbstractStorageClientOrganizationKeyServiceIntegrationTest : AbstractServiceIntegrationTest() {

    @Autowired
    protected lateinit var storageClientOrganizationKeyService: StorageClientOrganizationKeyService

    @Autowired
    protected lateinit var clientOrganizationIntegrationTestHelper: ClientOrganizationIntegrationTestHelper

    @Autowired
    protected var storageClientOrganizationKeyIntegrationTestHelper = StorageClientOrganizationKeyIntegrationTestHelper()
}