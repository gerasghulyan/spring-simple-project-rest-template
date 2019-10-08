package com.vntana.core.service.user;

import com.vntana.core.domain.User;
import com.vntana.core.service.user.dto.UserCreateDto;

/**
 * Created by Arthur Asatryan.
 * Date: 10/3/19
 * Time: 6:36 PM
 */
public interface UserService {

    User createUser(final UserCreateDto dto);
}
