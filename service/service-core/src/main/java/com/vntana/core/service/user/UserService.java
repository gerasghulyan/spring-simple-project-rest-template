package com.vntana.core.service.user;

import com.vntana.core.domain.user.User;
import com.vntana.core.domain.user.UserRole;
import com.vntana.core.service.user.dto.CreateUserDto;
import com.vntana.core.service.user.dto.UpdateUserDto;
import com.vntana.core.service.user.dto.UserGrantOrganizationRoleDto;

import java.util.List;
import java.util.Optional;

/**
 * Created by Arthur Asatryan.
 * Date: 10/3/19
 * Time: 6:36 PM
 */
public interface UserService {
    User create(final CreateUserDto dto);

    User update(final UpdateUserDto dto);

    Optional<User> findByEmail(final String email);

    Optional<User> findByUuid(final String uuid);

    User getByUuid(final String uuid);

    User makeVerified(final String email);

    User changePassword(final String uuid, final String password);

    void grantOrganizationRole(final UserGrantOrganizationRoleDto dto);

    boolean existsByUuid(final String uuid);

    boolean existsByEmail(final String email);

    boolean checkPassword(final String uuid, final String rawPassword);

    List<User> findByRoleAndOrganizationUuid(final UserRole userRole, final String organizationUuid);
}
