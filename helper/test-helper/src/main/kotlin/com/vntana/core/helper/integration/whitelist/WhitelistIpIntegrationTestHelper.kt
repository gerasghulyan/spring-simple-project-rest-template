package com.vntana.core.helper.integration.whitelist

import com.vntana.core.domain.organization.Organization
import com.vntana.core.domain.whitelist.WhitelistIp
import com.vntana.core.domain.whitelist.WhitelistType
import com.vntana.core.helper.integration.organization.OrganizationIntegrationTestHelper
import com.vntana.core.helper.unit.whitelist.WhitelistIpCommonTestHelper
import com.vntana.core.service.whitelist.WhitelistIpService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Arman Gevorgyan.
 * Date: 11/28/19
 * Time: 2:09 PM
 */
@Component
class WhitelistIpIntegrationTestHelper : WhitelistIpCommonTestHelper() {

    @Autowired
    private lateinit var whitelistIpService: WhitelistIpService

    @Autowired
    private lateinit var organizationHelper: OrganizationIntegrationTestHelper

    fun persistWhitelistIp(
        label: String = uuid(),
        ip: String = uuid(),
        organization: Organization = organizationHelper.persistOrganization(),
        type: WhitelistType = WhitelistType.EMBEDDED
    ): WhitelistIp {
        return whitelistIpService.create(buildCreateWhitelistIpDto(label, ip, organization.uuid, type))
    }
}