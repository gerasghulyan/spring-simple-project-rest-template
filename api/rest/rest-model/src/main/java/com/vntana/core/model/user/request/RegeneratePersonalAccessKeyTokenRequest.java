package com.vntana.core.model.user.request;

/**
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 3:44 PM
 */
public class RegeneratePersonalAccessKeyTokenRequest extends CreatePersonalAccessTokenRequest {

    public RegeneratePersonalAccessKeyTokenRequest() {
        super();
    }

    public RegeneratePersonalAccessKeyTokenRequest(final String token, final String userUuid) {
        super(token, userUuid);
    }
}
