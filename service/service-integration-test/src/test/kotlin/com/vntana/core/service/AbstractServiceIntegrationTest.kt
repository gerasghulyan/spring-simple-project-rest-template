package com.vntana.core.service

import com.vntana.core.service.configuration.DbInitializerConfiguration
import com.vntana.core.service.configuration.TestConfiguration
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

/**
 * Created by Arthur Asatryan.
 * Date: 10/4/19
 * Time: 2:25 PM
 *
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = [DbInitializerConfiguration::class, TestConfiguration::class])
abstract class AbstractServiceIntegrationTest : AbstractTransactionalJUnit4SpringContextTests() {

    @PersistenceContext
    lateinit var entityManager: EntityManager

    fun uuid(): String = UUID.randomUUID().toString()

    fun flush() {
        entityManager.flush()
    }

    fun clear() {
        entityManager.clear()
    }

    fun flushAndClear() {
        entityManager.flush()
        entityManager.clear()
    }
}