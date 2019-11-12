package com.vntana.core.rest.client.user;

import com.vntana.core.model.user.request.CreateUserRequest;
import com.vntana.core.model.user.request.FindUserByEmailRequest;
import com.vntana.core.model.user.request.SendUserVerificationRequest;
import com.vntana.core.model.user.request.VerifyUserRequest;
import com.vntana.core.model.user.response.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Arthur Asatryan.
 * Date: 10/18/19
 * Time: 3:46 PM
 */
@FeignClient(name = "coreUsers", path = "users", url = "${ms.core.url}")
public interface UserResourceClient {
    @PostMapping(path = "/create")
    CreateUserResponse createUser(@RequestBody final CreateUserRequest request);

    @PostMapping(path = "/by-email")
    FindUserByEmailResponse findByEmail(final FindUserByEmailRequest request);

    @GetMapping(path = "/{uuid}/account-details/organizations/{organizationUuid}")
    AccountUserResponse accountDetails(
            @PathVariable("uuid") final String uuid,
            @PathVariable("organizationUuid") final String organizationUuid
    );

    @PutMapping(path = "/verify")
    VerifyUserResponse verify(@RequestBody final VerifyUserRequest request);

    @PostMapping(path = "/send-verification")
    SendUserVerificationResponse sendVerification(@RequestBody final SendUserVerificationRequest request);

}
