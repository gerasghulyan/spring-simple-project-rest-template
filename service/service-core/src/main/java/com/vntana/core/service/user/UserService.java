package com.vntana.core.service.user;

import com.vntana.core.domain.user.User;
import com.vntana.core.domain.user.UserRole;
import com.vntana.core.service.user.dto.CreateUserDto;
import com.vntana.core.service.user.dto.CreateUserWithOwnerRoleDto;
import com.vntana.core.service.user.dto.UpdateUserDto;
import com.vntana.core.service.user.dto.GetOrCreateExternalUserDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Arthur Asatryan.
 * Date: 10/3/19
 * Time: 6:36 PM
 */
public interface UserService {
    
    User createWithOwnerRole(final CreateUserWithOwnerRoleDto dto);

    User create(final CreateUserDto dto);

    User update(final UpdateUserDto dto);

    Optional<User> findByEmail(final String email);

    Optional<User> findByUuid(final String uuid);

    Set<User> findByUuids(final Set<String> uuids);
    
    User getByUuid(final String uuid);

    User makeVerified(final String email);

    User changePassword(final String uuid, final String password);

    boolean existsByUuid(final String uuid);

    boolean existsByUuids(final Set<String> uuids);

    boolean existsByEmail(final String email);

    boolean checkPassword(final String uuid, final String rawPassword);

    List<User> findByRoleAndOrganizationUuid(final UserRole userRole, final String organizationUuid);

    Optional<User> findByEmailAndOrganizationUuid(final String email, final String organizationUuid);

    User getByEmail(final String email);

    User getOrCreateExternalUser(final GetOrCreateExternalUserDto getOrCreateExternalUserDto);
}
