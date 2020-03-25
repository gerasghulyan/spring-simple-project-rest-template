package com.vntana.core.rest.resource.indexation.impl

import com.vntana.core.rest.resource.indexation.AbstractIndexationWebTest
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

/**
 * Created by Manuk Gharslyan.
 * Date: 3/26/2020
 * Time: 12:23 PM
 */
class IndexationAllOrganizationsWebTest : AbstractIndexationWebTest() {

    @Test
    fun `test indexAllOrganizations`() {
        organizationResourceTestHelper.persistOrganization()
        organizationResourceTestHelper.persistOrganization()
        organizationResourceTestHelper.persistOrganization()
        assertBasicSuccessResultResponse(indexationResourceClient.indexAllOrganizations())
        verify(organizationUuidAwareActionProducer, times(6)).produce(ArgumentMatchers.any())
    }
}