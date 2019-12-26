package com.vntana.core.helper.invitation

import com.vntana.core.helper.AbstractRestTestHelper
import com.vntana.core.model.invitation.request.InvitationToPlatformRequest

/**
 * Created by Arman Gevorgyan.
 * Date: 12/26/19
 * Time: 10:52 AM
 */
open class InvitationRestTestHelper : AbstractRestTestHelper() {

    fun buildInvitationToPlatformRequest(token: String? = uuid(),
                                         email: String? = uuid()
    ): InvitationToPlatformRequest = InvitationToPlatformRequest(token, email)
}