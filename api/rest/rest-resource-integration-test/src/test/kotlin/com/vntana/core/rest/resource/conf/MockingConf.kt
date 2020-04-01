package com.vntana.core.rest.resource.conf

import com.sflpro.notifier.api.client.notification.email.EmailNotificationResourceClient
import com.vntana.cache.service.client.ClientOrganizationCacheService
import com.vntana.cache.service.organization.OrganizationCacheService
import com.vntana.cache.service.organization.OrganizationLockService
import com.vntana.cache.service.whitelist.WhitelistIpCacheService
import com.vntana.core.indexation.producer.invitation.organization.InvitationOrganizationUuidAwareActionProducer
import com.vntana.core.indexation.producer.organization.OrganizationUuidAwareActionProducer
import com.vntana.payment.client.customer.PaymentCustomerResourceClient
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

    @Bean
    @Primary
    fun whitelistIpCacheService(): WhitelistIpCacheService = mock(WhitelistIpCacheService::class.java)

    @Bean
    @Primary
    fun customerResourceClient(): PaymentCustomerResourceClient = mock(PaymentCustomerResourceClient::class.java)

    @Bean
    @Primary
    fun clientOrganizationCacheService(): ClientOrganizationCacheService = mock(ClientOrganizationCacheService::class.java)

    @Bean
    @Primary
    fun organizationUuidAwareActionProducer(): OrganizationUuidAwareActionProducer = mock(OrganizationUuidAwareActionProducer::class.java)

    @Bean
    @Primary
    fun invitationOrganizationUuidAwareActionProducer(): InvitationOrganizationUuidAwareActionProducer = mock(InvitationOrganizationUuidAwareActionProducer::class.java)

    @Bean
    @Primary
    fun organizationLockService(): OrganizationLockService = mock(OrganizationLockService::class.java)
}