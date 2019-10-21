package com.vntana.core.rest.facade.token.component;

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 5:22 PM
 */
public interface CreateResetPasswordTokenMediator {
    void create(final String userUuid);
}
