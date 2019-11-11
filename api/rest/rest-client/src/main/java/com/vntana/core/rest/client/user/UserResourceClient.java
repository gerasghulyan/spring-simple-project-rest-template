package com.vntana.core.rest.client.user;

import com.vntana.core.model.user.request.CreateUserRequest;
import com.vntana.core.model.user.request.FindUserByEmailRequest;
import com.vntana.core.model.user.request.VerifyUserRequest;
import com.vntana.core.model.user.response.CreateUserResponse;
import com.vntana.core.model.user.response.FindUserByEmailResponse;
import com.vntana.core.model.user.response.VerifyUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @PutMapping(path = "/verify")
    VerifyUserResponse verify(@RequestBody final VerifyUserRequest request);
}
