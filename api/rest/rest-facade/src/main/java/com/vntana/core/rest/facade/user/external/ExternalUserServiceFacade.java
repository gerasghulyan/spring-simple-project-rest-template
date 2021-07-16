package com.vntana.core.rest.facade.user.external;

import com.vntana.core.model.user.external.request.GetOrCreateExternalUserRequest;
import com.vntana.core.model.user.external.responce.GetOrCreateExternalUserResponse;

/**
 * Created by Diana Gevorgyan
 * Date: 7/14/2021
 * Time: 4:46 PM
 */
public interface ExternalUserServiceFacade {

    GetOrCreateExternalUserResponse getOrCreateExternalUser(final GetOrCreateExternalUserRequest request);
}
