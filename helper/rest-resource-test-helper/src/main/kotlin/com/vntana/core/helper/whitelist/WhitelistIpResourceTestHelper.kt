package com.vntana.core.helper.whitelist

import com.vntana.core.helper.organization.OrganizationResourceTestHelper
import com.vntana.core.model.whitelist.request.SaveWhitelistIpsRequest
import com.vntana.core.model.whitelist.request.WhitelistTypeModel
import com.vntana.core.model.whitelist.response.SaveWhitelistIpResponse
import com.vntana.core.rest.client.whitelist.WhitelistIpResourceClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Arman Gevorgyan.
 * Date: 11/29/19
 * Time: 12:41 PM
 */
@Component
class WhitelistIpResourceTestHelper : WhitelistIpRestTestHelper() {

    @Autowired
    private lateinit var whitelistIpResourceClient: WhitelistIpResourceClient

    @Autowired
    private lateinit var organizationResourceTestHelper: OrganizationResourceTestHelper

    fun persistWhitelistIps(
        organizationUuid: String = organizationResourceTestHelper.persistOrganization().response().uuid,
        type: WhitelistTypeModel = WhitelistTypeModel.API,
        request: SaveWhitelistIpsRequest = buildCreateOrUpdateWhitelistIpsRequest(
            organizationUuid = organizationUuid,
            type = type
        )
    ): SaveWhitelistIpResponse {
        return whitelistIpResourceClient.save(request)
    }
}