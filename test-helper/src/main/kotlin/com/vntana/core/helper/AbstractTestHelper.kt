package com.vntana.core.helper

import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 10/3/19
 * Time: 7:00 PM
 */
abstract class AbstractTestHelper {

    fun uuid(): String = UUID.randomUUID().toString()
}