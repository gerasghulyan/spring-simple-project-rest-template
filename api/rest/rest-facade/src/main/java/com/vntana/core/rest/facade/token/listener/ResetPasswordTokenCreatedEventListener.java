package com.vntana.core.rest.facade.token.listener;

import com.vntana.core.rest.facade.token.event.ResetPasswordTokenCreatedEvent;

/**
 * Created by Arthur Asatryan.
 * Date: 10/22/19
 * Time: 12:39 PM
 */
public interface ResetPasswordTokenCreatedEventListener {
    void listen(final ResetPasswordTokenCreatedEvent event);
}
