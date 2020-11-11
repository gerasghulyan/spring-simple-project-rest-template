package com.vntana.core.rest.client.auth;

import com.vntana.core.model.security.request.FindUserByUuidAndOrganizationRequest;
import com.vntana.core.model.security.response.SecureFindUserByEmailResponse;
import com.vntana.core.model.security.response.SecureFindUserByUuidAndOrganizationResponse;
import com.vntana.core.model.user.request.FindUserByEmailRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by Geras Ghulyan.
 * Date: 10/21/19
 * Time: 3:48 PM
 */
@FeignClient(name = "coreAuthResourceClient", path = "auth", url = "${ms.core.url}", primary = false)
public interface AuthResourceClient {

    @PostMapping(path = "/by-email")
    SecureFindUserByEmailResponse findByEmail(final FindUserByEmailRequest request);

    @PostMapping(path = "/by-user-and-organization")
    SecureFindUserByUuidAndOrganizationResponse findByUserAndOrganization(final FindUserByUuidAndOrganizationRequest request);
}
