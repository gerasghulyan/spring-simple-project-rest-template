package com.vntana.core.rest.facade.token.personalaccess;

import com.vntana.core.model.user.request.CreatePersonalAccessTokenRequest;
import com.vntana.core.model.user.response.personalaccess.PersonalAccessTokenResponse;

/**
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 1:38 AM
 */
public interface PersonalAccessTokenServiceFacade {
    
    PersonalAccessTokenResponse create(CreatePersonalAccessTokenRequest request);
}
