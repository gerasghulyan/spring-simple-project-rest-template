package com.vntana.core.rest.facade.token.component.impl

import com.vntana.core.persistence.utils.Executable
import com.vntana.core.persistence.utils.PersistenceUtilityService
import com.vntana.core.rest.facade.token.AbstractTokenServiceFacadeUnitTest
import com.vntana.core.rest.facade.token.component.CreateResetPasswordTokenMediator
import com.vntana.core.rest.facade.token.event.ResetPasswordTokenCreatedEvent
import com.vntana.core.service.token.dto.CreateResetPasswordTokenDto
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.*
import org.easymock.Mock
import org.junit.Before
import org.junit.Test
import org.springframework.context.ApplicationEventPublisher

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 6:13 PM
 */
class CreateResetPasswordTokenMediatorImplTest : AbstractTokenServiceFacadeUnitTest() {

    private lateinit var createResetPasswordTokenMediator: CreateResetPasswordTokenMediator

    @Mock
    private lateinit var persistenceUtilityService: PersistenceUtilityService

    @Mock
    private lateinit var applicationEventPublisher: ApplicationEventPublisher

    @Before
    fun beforeThis() {
        createResetPasswordTokenMediator = CreateResetPasswordTokenMediatorImpl(persistenceUtilityService, applicationEventPublisher, resetPasswordTokenService)
    }

    @Test
    fun `test create with invalid arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThatThrownBy { createResetPasswordTokenMediator.create(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test create`() {
        // test data
        resetAll()
        val userUuid = uuid()
        val resetPasswordToken = unitTestHelper.buildResetPasswordToken()
        // expectations
        expect(resetPasswordTokenService.create(CreateResetPasswordTokenDto(userUuid))).andReturn(resetPasswordToken)
        expect(persistenceUtilityService.runAfterTransactionCommitIsSuccessful(isA(Executable::class.java), eq(false)))
                .andAnswer { (getCurrentArguments()[0] as Executable).execute() }
        applicationEventPublisher.publishEvent(ResetPasswordTokenCreatedEvent(createResetPasswordTokenMediator, resetPasswordToken.uuid))
        replayAll()
        // test scenario
        createResetPasswordTokenMediator.create(userUuid)
        verifyAll()
    }
}