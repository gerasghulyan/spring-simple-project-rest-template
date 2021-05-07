package com.vntana.core.rest.facade.storage.client

import com.vntana.core.helper.storage.client.StorageClientOrganizationKeyRestTestHelper
import com.vntana.core.helper.unit.client.ClientOrganizationCommonTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.helper.unit.storage.client.StorageClientOrganizationKeyCommonTestHelper
import com.vntana.core.rest.facade.storage.client.impl.StorageClientOrganizationKeyServiceFacadeImpl
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.service.storage.client.StorageClientOrganizationKeyService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Geras Ghulyan
 * Date: 05-May-21
 * Time: 09:39
 */
abstract class AbstractStorageClientOrganizationKeyServiceFacadeUnitTest : AbstractFacadeUnitTest() {

    protected lateinit var storageClientOrganizationKeyServiceFacade: StorageClientOrganizationKeyServiceFacade
    
    @Mock
    protected lateinit var storageClientOrganizationKeyService: StorageClientOrganizationKeyService

    protected val storageClientOrganizationKeyRestTestHelper = StorageClientOrganizationKeyRestTestHelper()

    protected val clientOrganizationCommonTestHelper = ClientOrganizationCommonTestHelper()

    protected val storageClientOrganizationKeyCommonTestHelper = StorageClientOrganizationKeyCommonTestHelper()

    @Before
    fun before() {
        storageClientOrganizationKeyServiceFacade = StorageClientOrganizationKeyServiceFacadeImpl(
                storageClientOrganizationKeyService
        )
    }
}