package com.vntana.core.service.token

import com.vntana.core.service.AbstractServiceIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arman Gevorgyan.
 * Date: 3/20/20
 * Time: 6:06 PM
 */
abstract class AbstractTokenServiceIntegrationTest : AbstractServiceIntegrationTest() {

    @Autowired
    protected lateinit var tokenService: TokenService
}