package com.vntana.core.domain

import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 10/11/19
 * Time: 3:29 PM
 */
abstract class AbstractDomainUnitTest {
    fun uuid(): String = UUID.randomUUID().toString()
}