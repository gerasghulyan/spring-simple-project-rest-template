package com.vntana.core.rest.resource.conf

import com.sflpro.notifier.api.client.notification.email.EmailNotificationResourceClient
import com.vntana.cache.service.organization.OrganizationCacheService
import org.mockito.Mockito.mock
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

/**
 * Created by Arthur Asatryan.
 * Date: 11/7/19
 * Time: 6:47 PM
 */
@Configuration
class MockingConf {
    @Bean
    @Primary
    fun tagActionProducer(): EmailNotificationResourceClient = mock(EmailNotificationResourceClient::class.java)

    @Bean
    @Primary
    fun organizationCacheService(): OrganizationCacheService = mock(OrganizationCacheService::class.java)
}