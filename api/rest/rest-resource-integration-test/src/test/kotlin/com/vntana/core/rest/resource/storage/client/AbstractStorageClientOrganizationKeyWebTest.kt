package com.vntana.core.rest.resource.storage.client

import com.vntana.core.helper.client.ClientOrganizationResourceTestHelper
import com.vntana.core.helper.storage.client.StorageClientOrganizationKeyResourceTestHelper
import com.vntana.core.rest.client.storage.client.StorageClientOrganizationKeyResourceClient
import com.vntana.core.rest.resource.AbstractWebIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Geras Ghulyan
 * Date: 05-May-21
 * Time: 16:58
 */
abstract class AbstractStorageClientOrganizationKeyWebTest : AbstractWebIntegrationTest() {

    @Autowired
    protected lateinit var storageClientOrganizationKeyResourceClient: StorageClientOrganizationKeyResourceClient

    @Autowired
    protected val storageClientOrganizationKeyResourceTestHelper = StorageClientOrganizationKeyResourceTestHelper()

    @Autowired
    protected lateinit var clientOrganizationResourceTestHelper: ClientOrganizationResourceTestHelper
}