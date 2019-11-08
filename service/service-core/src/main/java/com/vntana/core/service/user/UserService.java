package com.vntana.core.service.user;

import com.vntana.core.domain.user.User;
import com.vntana.core.service.user.dto.CreateUserDto;

import java.util.Optional;

/**
 * Created by Arthur Asatryan.
 * Date: 10/3/19
 * Time: 6:36 PM
 */
public interface UserService {
    User create(final CreateUserDto dto);

    Optional<User> findByEmail(final String email);

    Optional<User> findByUuid(final String uuid);

    User getByUuid(final String uuid);
}
