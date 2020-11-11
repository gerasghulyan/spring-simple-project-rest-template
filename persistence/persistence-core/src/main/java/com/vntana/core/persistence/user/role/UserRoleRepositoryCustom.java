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

    List<AbstractUserRole> findAllByOrganization(final String organizationUuid);

    Optional<AbstractUserRole> findByOrganizationAndUser(final String organizationUuid, final String userUuid);

    List<AbstractUserRole> findAllOrganizationClientsByOrganization(final String organizationUuid);

    List<AbstractUserRole> findAllOrganizationClientsByOrganizationAndUser(final String organizationUuid, final String userUuid);

    Optional<AbstractUserRole> findByClientOrganizationAndUser(final String clientOrganizationUuid, final String userUuid);

    Optional<AbstractUserRole> findAdminRoleByUserAndOrganization(final String userUuid, final String organizationUuid);

    Optional<AbstractUserRole> findClientAdminRoleByUserAndClientOrganization(final String userUuid, final String clientOrganizationUuid);

    Optional<AbstractUserRole> findClientContentManagerRoleByUserAndClientOrganization(final String userUuid, final String clientOrganizationUuid);

    Optional<AbstractUserRole> findClientViewerRoleByUserAndClientOrganization(final String userUuid, final String clientOrganizationUuid);
}
