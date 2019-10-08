package com.vntana.core.rest.resource.user

import com.vntana.core.rest.resource.AbstractWebIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arthur Asatryan.
 * Date: 10/2/19
 * Time: 11:24 AM
 */
abstract class AbstractUserWebTest : AbstractWebIntegrationTest() {

    @Autowired
    lateinit var userHelper: UserWebHelper

    override fun baseMapping(): String = "http://localhost:${port}/user/"
}