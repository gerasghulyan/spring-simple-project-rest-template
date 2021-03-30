package com.vntana.core.model.user.request;

import com.vntana.core.model.security.request.CreatePersonalAccessTokenRequest;

/**
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 3:44 PM
 */
public class RegeneratePersonalAccessTokenRequest extends CreatePersonalAccessTokenRequest {

    public RegeneratePersonalAccessTokenRequest() {
        super();
    }

    public RegeneratePersonalAccessTokenRequest(final String token, final String userUuid) {
        super(token, userUuid);
    }
}
