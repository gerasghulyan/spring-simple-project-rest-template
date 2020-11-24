package com.vntana.core.rest.resource.user;

import com.vntana.commons.web.utils.ResponseEntityUtils;
import com.vntana.core.model.auth.response.UserRoleModel;
import com.vntana.core.model.user.request.*;
import com.vntana.core.model.user.response.*;
import com.vntana.core.model.user.response.account.AccountUserResponse;
import com.vntana.core.model.user.response.get.GetUsersByOrganizationResponse;
import com.vntana.core.model.user.response.get.GetUsersByRoleAndOrganizationUuidResponse;
import com.vntana.core.model.user.response.get.GetUsersByUuidsAndOrganizationUuidResponse;
import com.vntana.core.rest.facade.user.UserServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Arthur Asatryan.
 * Date: 10/2/19
 * Time: 10:41 AM
 */
@RestController
@RequestMapping(value = "/users",
        produces = "application/json"
)
public class UserResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

    private final UserServiceFacade userServiceFacade;

    public UserResource(final UserServiceFacade userServiceFacade) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.userServiceFacade = userServiceFacade;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody final CreateUserRequest request) {
        LOGGER.debug("Processing resource create for request - {}", request);
        final CreateUserResponse resultResponse = userServiceFacade.create(request);
        LOGGER.debug("Successfully processed resource create for request - {}", request);
        return ResponseEntity.ok(resultResponse);
    }

    @GetMapping(path = "existence/{email}")
    public ResponseEntity<ExistsUserByEmailResponse> existsByEmail(@PathVariable("email") final String email) {
        LOGGER.debug("Processing resource existsByEmail for email- {}", email);
        final ExistsUserByEmailResponse response = userServiceFacade.existsByEmail(email);
        LOGGER.debug("Successfully processed resource existsByEmail for email - {}", email);
        return ResponseEntityUtils.okWithStatusInHeader(response);
    }

    @PostMapping(path = "/by-email")
    public ResponseEntity<FindUserByEmailResponse> findByEmail(@RequestBody final FindUserByEmailRequest request) {
        LOGGER.debug("Processing find user by email request - {}", request);
        final FindUserByEmailResponse resultResponse = userServiceFacade.findByEmail(request);
        LOGGER.debug("Successfully proceeded find user by email request with response - {}", resultResponse);
        return ResponseEntity.ok(resultResponse);
    }

    @PostMapping(path = "/by-uuids-and-organization")
    public ResponseEntity<GetUsersByUuidsAndOrganizationUuidResponse> getByUuidsAndOrganizationUuid(@RequestBody GetByUuidsAndOrganizationUuidRequest request) {
        LOGGER.debug("Processing get users by uuids and organization uuid resource method for request - {}", request);
        final GetUsersByUuidsAndOrganizationUuidResponse resultResponse = userServiceFacade.getByUuidsAndOrganizationUuid(request);
        LOGGER.debug("Successfully processed get users by uuids and organization uuid resource method for request - {}", request);
        return ResponseEntity.ok(resultResponse);
    }

    @GetMapping(path = "/{uuid}")
    public ResponseEntity<FindUserByUuidResponse> findByUuid(@PathVariable("uuid") final String uuid) {
        LOGGER.debug("Processing find user by uuid resource method for uuid- {}", uuid);
        final FindUserByUuidResponse resultResponse = userServiceFacade.findByUuid(uuid);
        LOGGER.debug("Successfully processed find user by uuid resource method for uuid- {}", uuid);
        return ResponseEntity.ok(resultResponse);
    }

    @GetMapping(path = "/{uuid}/account-details")
    public ResponseEntity<AccountUserResponse> accountDetails(@PathVariable("uuid") final String uuid) {
        LOGGER.debug("Processing user facade accountDetails method by uuid - {}", uuid);
        final AccountUserResponse response = userServiceFacade.accountDetails(uuid);
        LOGGER.debug("Successfully processed user facade accountDetails method by uuid - {}", uuid);
        return ResponseEntityUtils.okWithStatusInHeader(response);
    }

    @GetMapping(path = "/userRoles/{role}/organizations/{organizationUuid}")
    public ResponseEntity<GetUsersByRoleAndOrganizationUuidResponse> getUsersByRoleAndOrganizationUuid(
            @PathVariable("role") final UserRoleModel role,
            @PathVariable("organizationUuid") final String organizationUuid) {
        LOGGER.debug("Processing retrieve users by userRole - {} and organizationUuid - {}", role, organizationUuid);
        final GetUsersByRoleAndOrganizationUuidResponse response = userServiceFacade.getByRoleAndOrganizationUuid(role, organizationUuid);
        LOGGER.debug("Successfully proceeded retrieve users by userRole - {} and organizationUuid - {}", role, organizationUuid);
        return ResponseEntityUtils.okWithStatusInHeader(response);
    }

    @GetMapping(path = "/organizations/{organizationUuid}")
    public ResponseEntity<GetUsersByOrganizationResponse> getUsersByOrganization(@PathVariable("organizationUuid") final String organizationUuid) {
        LOGGER.debug("Processing retrieve users by organizationUuid - {}", organizationUuid);
        final GetUsersByOrganizationResponse response = userServiceFacade.getByOrganizationUuid(organizationUuid);
        LOGGER.debug("Successfully proceeded retrieve users by organizationUuid - {}", organizationUuid);
        return ResponseEntityUtils.okWithStatusInHeader(response);
    }

    @GetMapping(path = "/client-organizations/{clientUuid}")
    public ResponseEntity<GetUsersByOrganizationResponse> getUsersByClientOrganization(@PathVariable("clientUuid") final String clientUuid) {
        LOGGER.debug("Processing retrieve client users by organizationUuid - {}", clientUuid);
        final GetUsersByOrganizationResponse response = userServiceFacade.getByClientOrganizationUuid(clientUuid);
        LOGGER.debug("Successfully proceeded client retrieve users by clientUuid - {}", clientUuid);
        return ResponseEntityUtils.okWithStatusInHeader(response);
    }

    @GetMapping(path = "/clients-organizations/{organizationUuid}")
    public ResponseEntity<GetUsersByOrganizationResponse> getUsersOfClientsByOrganization(@PathVariable("organizationUuid") final String organizationUuid) {
        LOGGER.debug("Processing retrieve clients related users by organizationUuid - {}", organizationUuid);
        final GetUsersByOrganizationResponse response = userServiceFacade.getClientsByOrganization(organizationUuid);
        LOGGER.debug("Successfully proceeded clients related retrieve users by organizationUuid - {}", organizationUuid);
        return ResponseEntityUtils.okWithStatusInHeader(response);
    }

    @PutMapping(path = "/verify")
    public ResponseEntity<VerifyUserResponse> verify(@RequestBody final VerifyUserRequest request) {
        LOGGER.debug("Processing user resource verify method for request - {}", request);
        final VerifyUserResponse resultResponse = userServiceFacade.verify(request.getEmail());
        LOGGER.debug("Successfully processed user resource verify method for request - {}", request);
        return ResponseEntity.ok(resultResponse);
    }

    @PostMapping(path = "/send-verification")
    public ResponseEntity<SendUserVerificationResponse> sendVerification(@RequestBody final SendUserVerificationRequest request) {
        LOGGER.debug("Processing user resource send verification method for request - {}", request);
        final SendUserVerificationResponse resultResponse = userServiceFacade.sendVerificationEmail(request);
        LOGGER.debug("Successfully processed user resource send verification method for request - {}", request);
        return ResponseEntity.ok(resultResponse);
    }

    @PostMapping(path = "/send-reset-password")
    public ResponseEntity<SendUserResetPasswordResponse> sendResetPasswordEmail(@RequestBody final SendUserResetPasswordRequest request) {
        LOGGER.debug("Processing user resource sendResetPasswordEmail method for request - {}", request);
        final SendUserResetPasswordResponse resultResponse = userServiceFacade.sendResetPasswordEmail(request);
        LOGGER.debug("Successfully processed user resource sendResetPasswordEmail method for request - {}", request);
        return ResponseEntity.ok(resultResponse);
    }

    @PutMapping(path = "/reset-password")
    public ResponseEntity<ResetUserPasswordResponse> resetPassword(@RequestBody final ResetUserPasswordRequest request) {
        LOGGER.debug("Processing user resource resetPassword method for email - {}", request.getToken());
        final ResetUserPasswordResponse resultResponse = userServiceFacade.resetPassword(request);
        LOGGER.debug("Successfully processed user resource resetPassword method for email - {}", request.getToken());
        return ResponseEntity.ok(resultResponse);
    }

    @GetMapping(path = "/check-reset-password-token/{token}")
    public ResponseEntity<ResetUserPasswordResponse> checkResetPasswordToken(@PathVariable("token") final String token) {
        LOGGER.debug("Processing user resource checkResetPassword method for email - {}", token);
        final ResetUserPasswordResponse resultResponse = userServiceFacade.checkResetPasswordToken(token);
        LOGGER.debug("Successfully processed user resource checkResetPassword method for email - {}", token);
        return ResponseEntity.ok(resultResponse);
    }

    @PutMapping(path = "/change-password")
    public ResponseEntity<ChangeUserPasswordResponse> changePassword(@RequestBody final ChangeUserPasswordRequest request) {
        LOGGER.debug("Processing user resource changePassword method for request - {}", request);
        final ChangeUserPasswordResponse response = userServiceFacade.changePassword(request);
        LOGGER.debug("Successfully processed user resource changePassword method for request - {}", request);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<UpdateUserResponse> update(@RequestBody final UpdateUserRequest request) {
        LOGGER.debug("Processing user resource update method for request - {}", request);
        final UpdateUserResponse response = userServiceFacade.update(request);
        LOGGER.debug("Successfully processed user resource update method for request - {}", request);
        return ResponseEntity.ok(response);
    }
}
