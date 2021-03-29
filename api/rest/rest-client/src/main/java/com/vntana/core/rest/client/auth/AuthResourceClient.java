package com.vntana.core.rest.client.auth;

import com.vntana.core.model.security.request.CreatePersonalAccessTokenRequest;
import com.vntana.core.model.security.request.FindUserByPersonalAccessTokenRequest;
import com.vntana.core.model.security.request.FindUserByUuidAndClientOrganizationRequest;
import com.vntana.core.model.security.request.FindUserByUuidAndOrganizationRequest;
import com.vntana.core.model.security.response.*;
import com.vntana.core.model.user.request.FindUserByEmailRequest;
import com.vntana.core.model.user.request.RegeneratePersonalAccessTokenRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(path = "/by-user-and-client-organization")
    SecureFindUserByUuidAndClientOrganizationResponse findByUserAndClientOrganization(final FindUserByUuidAndClientOrganizationRequest request);

    @PostMapping(path = "/by-personal-access-token")
    SecureFindUserByPersonalAccessTokenResponse findByPersonalAccessToken(final FindUserByPersonalAccessTokenRequest request);

    @PostMapping(path = "/create/personal-access-token")
    PersonalAccessTokenResponse createPersonalAccessToken(final CreatePersonalAccessTokenRequest request);

    @PostMapping(path = "/expire/personal-access-token/{userUuid}")
    PersonalAccessTokenExpireResponse expirePersonalAccessTokenByUserUuid(@PathVariable("userUuid") final String userUuid);

    @GetMapping(path = "/personal-access-token/{userUuid}")
    PersonalAccessTokenResponse findPersonalAccessTokenByUserUuid(@PathVariable("userUuid") final String userUuid);

    @PutMapping(path = "personal-access-token/regenerate")
    PersonalAccessTokenResponse regeneratePersonalAccessToken(@RequestBody final RegeneratePersonalAccessTokenRequest request);
}
