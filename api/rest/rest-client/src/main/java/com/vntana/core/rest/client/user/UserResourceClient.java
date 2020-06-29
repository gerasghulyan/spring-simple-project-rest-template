package com.vntana.core.rest.client.user;

import com.vntana.core.model.auth.response.UserRoleModel;
import com.vntana.core.model.user.request.*;
import com.vntana.core.model.user.response.*;
import com.vntana.core.model.user.response.account.AccountUserResponse;
import com.vntana.core.model.user.response.get.GetUsersByOrganizationResponse;
import com.vntana.core.model.user.response.get.GetUsersByRoleAndOrganizationUuidResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Arthur Asatryan.
 * Date: 10/18/19
 * Time: 3:46 PM
 */
@FeignClient(name = "coreUsers", path = "users", url = "${ms.core.url}", primary = false)
public interface UserResourceClient {
    @PostMapping(path = "/create")
    CreateUserResponse createUser(@RequestBody final CreateUserRequest request);

    @GetMapping(path = "existence/{email}")
    ResponseEntity<ExistsUserByEmailResponse> existsByEmail(@PathVariable("email") final String email);

    @PostMapping(path = "/by-email")
    FindUserByEmailResponse findByEmail(final FindUserByEmailRequest request);

    @GetMapping(path = "/{uuid}")
    FindUserByUuidResponse findByUuid(@PathVariable("uuid") final String uuid);

    @GetMapping(path = "/{uuid}/account-details")
    ResponseEntity<AccountUserResponse> accountDetails(@PathVariable("uuid") final String uuid);

    @GetMapping(path = "/userRoles/{role}/organizations/{organizationUuid}")
    ResponseEntity<GetUsersByRoleAndOrganizationUuidResponse> getUsersByRoleAndOrganizationUuid(
            @PathVariable("role") final UserRoleModel role,
            @PathVariable("organizationUuid") final String organizationUuid);

    @GetMapping(path = "/organizations/{organizationUuid}")
    ResponseEntity<GetUsersByOrganizationResponse> getUsersByOrganization(@PathVariable("organizationUuid") final String organizationUuid);

    @PutMapping(path = "/verify")
    VerifyUserResponse verify(@RequestBody final VerifyUserRequest request);

    @PostMapping(path = "/send-verification")
    SendUserVerificationResponse sendVerification(@RequestBody final SendUserVerificationRequest request);

    @PostMapping(path = "/send-reset-password")
    SendUserResetPasswordResponse sendResetPassword(@RequestBody final SendUserResetPasswordRequest request);

    @PutMapping(path = "/reset-password")
    ResetUserPasswordResponse resetPassword(@RequestBody final ResetUserPasswordRequest request);

    @GetMapping(path = "/check-reset-password-token/{token}")
    ResetUserPasswordResponse checkResetPasswordToken(@PathVariable("token") final String token);

    @PutMapping(path = "/change-password")
    ChangeUserPasswordResponse changePassword(@RequestBody final ChangeUserPasswordRequest request);

    @PutMapping
    UpdateUserResponse update(@RequestBody final UpdateUserRequest request);
}
