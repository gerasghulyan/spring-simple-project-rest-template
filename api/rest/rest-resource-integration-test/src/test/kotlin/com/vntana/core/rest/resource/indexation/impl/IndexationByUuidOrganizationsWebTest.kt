package com.vntana.core.rest.resource.indexation.impl

import com.vntana.commons.queue.model.MessageActionType
import com.vntana.core.rest.resource.indexation.AbstractIndexationWebTest
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify

/**
 * Created by Manuk Gharslyan.
 * Date: 3/26/2020
 * Time: 12:23 PM
 */
class IndexationByUuidOrganizationsWebTest : AbstractIndexationWebTest() {

    @Test
    fun `test reindexByUuid`() {
        val resultResponse = userResourceTestHelper.persistUser()
        val organizationUuid = resultResponse.response().organizationUuid
        assertBasicSuccessResultResponse(indexationResourceClient.reindexByUuid(organizationUuid))
        verify(organizationUuidAwareActionProducer).produce(Mockito.argThat { argument ->
            argument.messageActionType == MessageActionType.UPDATED && argument.uuid == organizationUuid
        })
    }
}