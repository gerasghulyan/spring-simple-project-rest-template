package com.project.sample.helper

import java.util.*

/**
 * Created by Geras Ghulyan.
 * Date: 10/3/19
 * Time: 7:00 PM
 */
abstract class AbstractTestHelper {
    fun uuid(): String = UUID.randomUUID().toString()
}