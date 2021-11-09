package com.project.sample.service.configuration

import org.springframework.context.annotation.Configuration
import org.testcontainers.containers.PostgreSQLContainer

/**
 * Created by Geras Ghulyan.
 * Date: 10/4/19
 * Time: 3:00 PM
 */
@Configuration
class ServiceIntegrationTestDbInitializerConfiguration {
    companion object {
        init {
            val postgreSQLContainer: PostgreSQLContainer<*> = PostgreSQLContainer<Nothing>()
            postgreSQLContainer.start()
            System.setProperty("spring.datasource.url", postgreSQLContainer.getJdbcUrl())
            System.setProperty("spring.datasource.username", postgreSQLContainer.getUsername())
            System.setProperty("spring.datasource.password", postgreSQLContainer.getPassword())
            System.setProperty("spring.datasource.driverClassName", "org.postgresql.Driver")
            System.setProperty("spring.jpa.properties.hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
            System.setProperty("spring.flyway.url", postgreSQLContainer.getJdbcUrl())
            System.setProperty("spring.flyway.user", postgreSQLContainer.getUsername())
            System.setProperty("spring.flyway.password", postgreSQLContainer.getPassword())
            System.setProperty("spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation", "true")
//            System.setProperty("spring.jpa.hibernate.ddl-auto", "create")
            System.setProperty("spring.jpa.show-sql", "true")
        }
    }
}