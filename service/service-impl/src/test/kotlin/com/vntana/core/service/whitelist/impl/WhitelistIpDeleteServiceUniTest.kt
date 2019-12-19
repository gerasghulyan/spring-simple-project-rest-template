package com.vntana.core.service.whitelist.impl

import com.vntana.core.service.whitelist.AbstractWhitelistIpServiceUnitTest
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 11/28/19
 * Time: 3:48 PM
 */
class WhitelistIpDeleteServiceUniTest : AbstractWhitelistIpServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { whitelistIpService.delete(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { whitelistIpService.delete(listOf()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test`() {
        val uuids = listOf(uuid(), uuid())
        resetAll()
        expect(whitelistIpRepository.deleteByUuid(uuids)).andVoid()
        replayAll()
        whitelistIpService.delete(uuids)
        verifyAll()
    }
}