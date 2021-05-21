package com.vntana.core.helper.whitelist

import com.vntana.commons.helper.AbstractRestTestHelper
import com.vntana.core.model.whitelist.request.CreateOrUpdateWhitelistIpItemRequestModel
import com.vntana.core.model.whitelist.request.GetWhitelistIpsRequest
import com.vntana.core.model.whitelist.request.SaveWhitelistIpsRequest
import com.vntana.core.model.whitelist.request.WhitelistTypeModel
import com.vntana.core.model.whitelist.response.model.GetWhitelistIpResponseModel
import kotlin.random.Random

/**
 * Created by Arman Gevorgyan.
 * Date: 11/29/19
 * Time: 12:40 PM
 */
open class WhitelistIpRestTestHelper : AbstractRestTestHelper() {

    fun buildCreateOrUpdateWhitelistIpsRequest(
        organizationUuid: String? = uuid(),
        whitelistIps: List<CreateOrUpdateWhitelistIpItemRequestModel>? = listOf(
            buildCreateOrUpdateWhitelistIpItemRequestModel()
        ),
        type: WhitelistTypeModel? = WhitelistTypeModel.API
    ): SaveWhitelistIpsRequest = SaveWhitelistIpsRequest(organizationUuid, whitelistIps, type)

    fun buildGetWhitelistIpsRequest(
        organizationUuid: String? = uuid(),
        type: WhitelistTypeModel? = WhitelistTypeModel.API
    ): GetWhitelistIpsRequest = GetWhitelistIpsRequest(organizationUuid, type)

    fun buildCreateOrUpdateWhitelistIpItemRequestModel(
        label: String? = uuid(),
        ip: String? = validIp()
    ): CreateOrUpdateWhitelistIpItemRequestModel {
        return CreateOrUpdateWhitelistIpItemRequestModel(label, ip)
    }

    fun buildGetWhitelistIpResponseModel(
        uuid: String? = uuid(), label: String? = uuid(), ip: String = uuid(), organizationUuid: String? = uuid()
    ): GetWhitelistIpResponseModel = GetWhitelistIpResponseModel(uuid, label, ip, organizationUuid)

    fun validIp(): String {
        return "${Random.nextInt(255)}.${Random.nextInt(255)}.${Random.nextInt(255)}.${Random.nextInt(255)}"
    }
}