package com.vntana.core.rest.resource

import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 10/2/19
 * Time: 11:26 AM
 */
abstract class AbstractWebHelper {
    fun uuid(): String = UUID.randomUUID().toString()
}