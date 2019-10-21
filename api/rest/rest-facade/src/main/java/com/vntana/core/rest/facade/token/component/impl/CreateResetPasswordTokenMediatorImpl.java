package com.vntana.core.rest.facade.token.component.impl;

import com.vntana.core.domain.token.ResetPasswordToken;
import com.vntana.core.persistence.utils.PersistenceUtilityService;
import com.vntana.core.rest.facade.token.component.CreateResetPasswordTokenMediator;
import com.vntana.core.rest.facade.token.event.ResetPasswordTokenCreatedEvent;
import com.vntana.core.service.token.ResetPasswordTokenService;
import com.vntana.core.service.token.dto.CreateResetPasswordTokenDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 5:23 PM
 */
@Component
public class CreateResetPasswordTokenMediatorImpl implements CreateResetPasswordTokenMediator {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateResetPasswordTokenMediatorImpl.class);

    private final PersistenceUtilityService persistenceUtilityService;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final ResetPasswordTokenService resetPasswordTokenService;

    public CreateResetPasswordTokenMediatorImpl(final PersistenceUtilityService persistenceUtilityService,
                                                final ApplicationEventPublisher applicationEventPublisher,
                                                final ResetPasswordTokenService resetPasswordTokenService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.persistenceUtilityService = persistenceUtilityService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.resetPasswordTokenService = resetPasswordTokenService;
    }

    @Transactional
    @Override
    public void create(final String userUuid) {
        Assert.notNull(userUuid, "The userUuid should not be null");
        final ResetPasswordToken resetPasswordToken = resetPasswordTokenService.create(new CreateResetPasswordTokenDto(userUuid));
        persistenceUtilityService.runAfterTransactionCommitIsSuccessful(() ->
                applicationEventPublisher.publishEvent(new ResetPasswordTokenCreatedEvent(this, resetPasswordToken.getUuid())), false
        );
    }
}
