package com.vntana.core.helper.unit.whitelist

import com.vntana.core.domain.organization.Organization
import com.vntana.core.domain.whitelist.WhitelistIp
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import com.vntana.core.service.whitelist.dto.CreateWhitelistIpDto
import com.vntana.core.service.whitelist.dto.UpdateWhitelistIpDto

/**
 * Created by Arman Gevorgyan.
 * Date: 11/28/19
 * Time: 2:06 PM
 */
open class WhitelistIpCommonTestHelper : AbstractCommonTestHelper() {

    fun buildWhitelistIp(label: String? = uuid(), ip: String? = uuid(), organization: Organization?
    ): WhitelistIp = WhitelistIp(label, ip, organization)

    fun buildCreateWhitelistIpDto(label: String? = uuid(), ip: String? = uuid(), organizationUuid: String? = uuid()
    ): CreateWhitelistIpDto = CreateWhitelistIpDto(label, ip, organizationUuid)

    fun buildUpdateWhitelistIpDto(uuid: String? = uuid(), label: String? = uuid(), ip: String? = uuid()
    ): UpdateWhitelistIpDto = UpdateWhitelistIpDto(uuid, label, ip)
}