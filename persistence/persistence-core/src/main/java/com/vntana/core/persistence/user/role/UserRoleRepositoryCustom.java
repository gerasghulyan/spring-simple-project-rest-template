package com.vntana.core.persistence.user.role;

import com.vntana.core.domain.user.AbstractUserRole;

import java.util.List;
import java.util.Optional;

/**
 * Created by Vardan Aivazian
 * Date: 04.11.2020
 * Time: 15:22
 */
public interface UserRoleRepositoryCustom {

    List<AbstractUserRole> findAllByOrganizationUuid(final String organizationUuid);

    Optional<AbstractUserRole> findAllByOrganizationAndUser(final String organizationUuid, final String userUuid);
}
